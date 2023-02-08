package com.example.demo.service;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class GraphQLService {

    private GraphQL graphQL;
    @Autowired
    Book book;

    @Value("schema.graphql")
    private ClassPathResource classPathLoader;

    @PostConstruct
    private void loadSchema() throws IOException{
        InputStream st= classPathLoader.getInputStream();
        Reader targetReader= new InputStreamReader(st);
        TypeDefinitionRegistry typeDefinitionRegistry= new SchemaParser().parse(targetReader);
        RuntimeWiring runtimeWiring=buildRuntimeWiring();
        GraphQLSchema graphQLSchema =new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry,runtimeWiring);
        graphQL =GraphQL.newGraphQL(graphQLSchema).build();

    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring().type("Query",typeWiring->typeWiring.dataFetcher("books",book)).build();
    }

}
