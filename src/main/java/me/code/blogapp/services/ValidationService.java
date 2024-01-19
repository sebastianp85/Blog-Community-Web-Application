package me.code.blogapp.services;

import org.springframework.stereotype.Component;

@Component
public class ValidationService {

    public void validatePostContent(String content) {
        if (containsInvalidCharacters(content)) {
            throw new IllegalArgumentException("Invalid characters or patterns in post content.");
        }
    }

    public void validateInput(String input) {
        validateSpecialCharacters(input);
    }

    private void validateSpecialCharacters(String input) {
        if (input != null && !input.matches("^[a-zA-Z0-9@.]+$")) {
            throw new IllegalArgumentException("Invalid characters in the input");
        }
    }

    private boolean containsInvalidCharacters(String content) {
        return content != null && (
                content.contains("<") || content.contains(">") ||
                        content.contains("\"") || content.contains("'") ||
                        content.toLowerCase().contains("onmouseover") ||
                        content.toLowerCase().contains("onclick") ||
                        content.toLowerCase().contains("javascript:")
        );
    }
}
