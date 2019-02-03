const {
    ApolloServer
} = require('apollo-server');
const {
    GraphQLScalarType
} = require('graphql');

const typeDefs = `
    scalar DateTime
    enum PhotoCategory {
        SELFIE
        PORTRAIT
        ACTION
        LANDSCAPE
        GRAPHIC
    }
    input PostPhotoInput {
        name: String!
        category: PhotoCategory=PORTRAIT
        description: String
        githubUser: String!
    }
    type Query {
        totalPhotos: Int!
        allPhotos: [Photo!]!
        recentPhotos(after: DateTime!): [Photo!]!
        allUsers: [User!]!
    }
    type Mutation {
        postPhoto(input: PostPhotoInput!): Photo!
        postUser(name: String! avatar: String): User!
    }
    type User {
        githubLogin: ID!
        name: String
        avatar: String
        postedPhotos: [Photo!]
    }
    type Photo {
        id: ID!
        url: String!
        name: String!
        description: String
        category: PhotoCategory!
        postedBy: User!
        created: DateTime!
    }
`;

var photoId = 0;
var userId = 0;
const photos = [];
const users = [];
const resolvers = {
        Query: {
            totalPhotos: () => photos.length,
            allPhotos: () => photos,
            allUsers: () => users,
            recentPhotos: (parent, args) => photos.filter(photo => new Date(photo.created) - new Date(args.after) >= 0)
        },
    Mutation: {
        postPhoto(parent, args) {
            var newPhoto = {
                id: photoId++,
                ...args.input,
                created: new Date()
            }
            photos.push(newPhoto);
            return newPhoto;
        },
        postUser(parent, args) {
            var user = {
                githubLogin: userId++,
                ...args
            }
            users.push(user);
            return user;
        }
    },
    Photo: {
        url: parent => `http://yoursite.com/img/${parent.id}.jpg`,
        postedBy: parent => users.find(u => u.githubLogin == parent.githubUser)
    },
    User: {
        postedPhotos: parent => photos.filter(p => p.githubUser == parent.githubLogin)
    },
    DateTime: new GraphQLScalarType({
        name: 'DateTime',
        description: 'A valid date time value.',
        parseValue: value => new Date(value),
        serialize: value => new Date(value).toISOString(),
        parseLiteral: ast => ast.value
    })
};

const server = new ApolloServer({
    typeDefs,
    resolvers
});

server
    .listen()
    .then(({
        url
    }) => console.log(`GraphQL Service running on ${url}`))