package com.task.solution.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.solution.dto.AuthorDto;
import com.task.solution.exception.EntityDoesNotExistException;
import com.task.solution.mapper.Converter;
import com.task.solution.model.Author;
import com.task.solution.model.Book;
import com.task.solution.model.Comic;
import com.task.solution.model.Magazine;
import com.task.solution.repository.AuthorRepository;
import com.task.solution.repository.BookRepository;
import com.task.solution.repository.ComicRepository;
import com.task.solution.repository.MagazineRepository;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private Converter converter;

	@Autowired
	private AuthorRepository authorRespository;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ComicRepository comicRepository;
	
	@Autowired
	private MagazineRepository magazineRepository;

	@Override
	public AuthorDto createAuthor(AuthorDto authorDto) {

		Author author = (Author) this.converter.convert(authorDto, Author.class);

		Author savedAuthor = this.authorRespository.save(author);

		return (AuthorDto) this.converter.convert(savedAuthor, AuthorDto.class);
	}

	@Override
	public List<AuthorDto> getAuthors() {

		List<Author> authorList = (List<Author>) this.authorRespository.findAll();

		return authorList.stream().map(author -> (AuthorDto) this.converter.convert(author, AuthorDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public void deleteAuthor(int authorId) throws EntityDoesNotExistException {
		Optional<Author> author = this.authorRespository.findById(authorId);
		if (author.isPresent()) {
			this.authorRespository.delete(author.get());
		} else {
			throw new EntityDoesNotExistException("Author ID does not exist");
		}
	}

	@Override
	public AuthorDto getAuthorById(int authorId) throws EntityDoesNotExistException {
		Optional<Author> author = this.authorRespository.findById(authorId);
		if (author.isPresent()) {
			return (AuthorDto) this.converter.convert(author.get(), AuthorDto.class);
		} else {
			throw new EntityDoesNotExistException("Author ID does not exist");
		}
	}

	@Override
	public AuthorDto updateAuthorDetails(int authorId, AuthorDto authorDto) throws EntityDoesNotExistException {
		Optional<Author> savedAuthor = this.authorRespository.findById(authorId);
		Author updatedAuthor = (Author) this.converter.convert(authorDto, Author.class);
		if (savedAuthor.isPresent()) {
			Author author = savedAuthor.get();
			if (!Objects.isNull(updatedAuthor.getAuthorName())) {
				author.setAuthorName(updatedAuthor.getAuthorName());
			}
			if (!Objects.isNull(updatedAuthor.getBooks()) && !updatedAuthor.getBooks().isEmpty()) {
				for (Book book : updatedAuthor.getBooks()) {
					if (this.bookRepository.existsById(book.getBookId())) {
						author.getBooks().add(book);
					}
				}
			}
			if (!Objects.isNull(updatedAuthor.getComics()) && !updatedAuthor.getComics().isEmpty()) {
				for(Comic comic: updatedAuthor.getComics()) {
					if(this.comicRepository.existsById(comic.getComicId())) {
						author.getComics().add(comic);
					}
				}
			}
			if (!Objects.isNull(updatedAuthor.getMagazines()) && !updatedAuthor.getMagazines().isEmpty()) {
				for(Magazine magazine: updatedAuthor.getMagazines()) {
					if(this.magazineRepository.existsById(magazine.getMagazineId())) {
						author.getMagazines().add(magazine);
					}
				}
			}
			return (AuthorDto) this.converter.convert(author, AuthorDto.class);
		} else {
			throw new EntityDoesNotExistException("Author ID Does not exist.");
		}
	}

}
