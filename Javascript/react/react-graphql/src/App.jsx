import React from 'react';
import {ApolloProvider} from "react-apollo";
import ApolloClient from "apollo-boost";
import Users from './Users'

const client = new ApolloClient({uri: "http://localhost:4000"});

const App = () => {
  return (
    <ApolloProvider client={client}>
      <div className="App">
        <header className="App-header">
          <Users/>
        </header>
      </div>
    </ApolloProvider>
  );

}

export default App;
