import React, {useEffect} from 'react';
import {Query} from "react-apollo";
import gql from "graphql-tag";

const users = () => {
    useEffect(() => {
        document.title = 'effect';
        return () => {
            document.title = 'unmount';
        };
    });
    return (
        <Query
            query={gql `{allUsers{ githubLogin name avatar postedPhotos { category description } } }`}>
            {({loading, error, data}) => {
                if (loading) 
                    return <p>Loading...</p>;
                if (error) {
                    console.log(error);
                    return <p>Error :</p>;
                }
                return data
                    .allUsers
                    .map(({name, avatar}) => {
                        return (
                            <div key={name}>
                                <p>{name}: {avatar}</p>
                            </div>
                        )
                    });
            }}
        </Query>
    );
}

export default users;