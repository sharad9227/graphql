package repository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import model.Book;

import java.util.List;

public class BookFetcher implements DataFetcher<Book> {
    @Override
    public List<Book> get(DataFetchingEnvironment dataFetchingEnvironment)
    {
        Book book =new Book();

    }
}
