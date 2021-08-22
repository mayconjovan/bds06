package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ReviewRepository repository;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		User usuarioLogado = authService.authenticated();
		UserDTO dtoUser = userService.findById(usuarioLogado.getId());

		User user = new User();
		user.setEmail(dtoUser.getEmail());
		user.setId(dtoUser.getId());
		user.setName(dtoUser.getName());
		
		Review entity = new Review();

		entity.setText(dto.getText());
		entity.setMovie(movieRepository.getOne(dto.getMovieId()));
		entity.setUser(user);

		entity = repository.save(entity);

		return new ReviewDTO(entity);
	}

}
