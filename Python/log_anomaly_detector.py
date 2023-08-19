from dotenv import load_dotenv
from langchain.chains import RetrievalQA
from langchain.chat_models import ChatOpenAI
from langchain.document_loaders import DirectoryLoader
from langchain.embeddings import SentenceTransformerEmbeddings
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain.vectorstores import Chroma

load_dotenv()


# function to load the text docs
def load_docs(directory):
    loader = DirectoryLoader(directory)
    documents = loader.load()
    return documents


def split_docs(documents, chunk_size=1000, chunk_overlap=20):
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=chunk_size, chunk_overlap=chunk_overlap)
    docs = text_splitter.split_documents(documents)
    return docs


def open_ai(query, directory):
    documents = load_docs(directory)
    docs = split_docs(documents)
    embeddings = SentenceTransformerEmbeddings(model_name="all-MiniLM-L6-v2")
    db = Chroma.from_documents(docs, embeddings)
    model_name = "gpt-3.5-turbo"
    llm = ChatOpenAI(model_name=model_name)
    qa_chain = RetrievalQA.from_chain_type(llm, retriever=db.as_retriever())
    qa_chain({"query": '''Use this as context to answer the following questions. Given set of log lines like this
                       Jun 28 08:10:24 combo sshd(pam_unix)[11513]: authentication failure; logname= uid=0 euid=0 tty=NODEVssh ruser= rhost=61.53.154.93  user=root
                       You need to print only the lines containing error
                       And explain anomaly as User was denied login once. It is suspicious attempt
                       '''})
    answer = qa_chain({"query": query})
    print(answer['result'])


open_ai("Return all anomalies", 'data/logs')
