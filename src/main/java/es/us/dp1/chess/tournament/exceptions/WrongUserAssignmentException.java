package es.us.dp1.chess.tournament.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class WrongUserAssignmentException extends RuntimeException {

	private static final long serialVersionUID = -3906338266891937099L;

	public WrongUserAssignmentException (final Object object) {
		super(String.format("%s is not playing.", object.getClass().getSimpleName()));
	}

	public WrongUserAssignmentException(final String message) {
		super(message);
	}

}
