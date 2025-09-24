package zw.co.zss.bookManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private Long id;
    private String name;


    public AuthorDto(Long id, String firstName, String lastName) {
    }
}
