package org.preproject.springboot.springbootpractice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "There are duplicate email. Please, enter another email.")
public class ThereAreDupeEmailInDb extends RuntimeException {

}
