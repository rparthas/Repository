from dotenv import load_dotenv
from langchain import OpenAI, LLMChain, PromptTemplate
from langchain.chains.question_answering import load_qa_chain
from langchain.chains.summarize import load_summarize_chain
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


def get_matching_docs(query, directory):
    documents = load_docs(directory)
    docs = split_docs(documents)
    embeddings = SentenceTransformerEmbeddings(model_name="all-MiniLM-L6-v2")
    db = Chroma.from_documents(docs, embeddings)
    docs = db.similarity_search(query)
    print(docs[0])
    return docs


def open_ai(query, directory):
    model_name = "gpt-3.5-turbo"
    llm = ChatOpenAI(model_name=model_name)
    chain = load_qa_chain(llm, chain_type="stuff", verbose=True)
    answer = chain.run(input_documents=get_matching_docs(query, directory), question=query)
    print(answer)


def generate_company_name(name):
    llm = OpenAI(temperature=0.9)
    # Write a prompt using PromptTemplate
    prompt = PromptTemplate(
        input_variables=["product"],
        template="What is a good name for a company that makes {product}?",
    )
    # create chain using LLMChain
    chain = LLMChain(llm=llm, prompt=prompt)

    # Run the chain only specifying the input variable.
    print(chain.run(name))


def summarize(directory):
    documents = load_docs(directory)
    docs = split_docs(documents)
    llm = OpenAI(temperature=0.9)
    chain = load_summarize_chain(llm, chain_type="map_reduce")
    print(chain.run(docs))


get_matching_docs("What are the different kinds of pets people commonly own?", 'data/pets')
open_ai("What are the emotional benefits of owning a pet?", 'data/pets')
generate_company_name("colorful socks")
summarize('data/pets')
