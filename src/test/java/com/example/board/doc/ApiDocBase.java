package com.example.board.doc;

import com.example.board.config.BoardUserTokenFilter;
import jakarta.servlet.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
public abstract class ApiDocBase {
    protected MockMvc mockMvc;

    public abstract Object getApi();

    public boolean applyTokenFilter() {
        return true;
    }

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(getApi())
                .addFilters(addFilters())
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    private Filter[] addFilters() {
        CharacterEncodingFilter utf8EncodingFilter = new CharacterEncodingFilter("UTF-8", true);

        if (applyTokenFilter()) {
            return new Filter[]{utf8EncodingFilter, new BoardUserTokenFilter()};
        }

        return new Filter[]{utf8EncodingFilter};
    }

    protected FieldDescriptor[] apiArrayResponseFieldDescriptor() {
        return new FieldDescriptor[]{
                fieldWithPath("code").type(STRING).description("결과 코드"),
                fieldWithPath("message").type(STRING).description("결과 메시지"),
                fieldWithPath("data").type(ARRAY).description("데이터")
        };
    }

    protected FieldDescriptor[] apiObjectResponseFieldDescriptor() {
        return new FieldDescriptor[]{
                fieldWithPath("code").type(STRING).description("결과 코드"),
                fieldWithPath("message").type(STRING).description("결과 메시지"),
                fieldWithPath("data").type(OBJECT).optional().description("데이터")
        };
    }
}
