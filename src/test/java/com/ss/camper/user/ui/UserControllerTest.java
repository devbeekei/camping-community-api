package com.ss.camper.user.ui;

import com.ss.camper.common.ControllerTest;
import com.ss.camper.common.WithMockCustomUser;
import com.ss.camper.common.util.JWTUtil;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import com.ss.camper.user.application.UserAgreeTermsService;
import com.ss.camper.user.application.UserProfileImageService;
import com.ss.camper.user.application.UserService;
import com.ss.camper.user.application.dto.UserInfoDTO;
import com.ss.camper.user.domain.TermsType;
import com.ss.camper.user.domain.UserType;
import com.ss.camper.user.ui.payload.SignUpPayload;
import com.ss.camper.user.ui.payload.UpdateUserInfoPayload;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static com.ss.camper.common.ApiDocumentAttributes.userTypeAttribute;
import static com.ss.camper.common.ApiDocumentUtil.*;
import static com.ss.camper.user.UserMock.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserAgreeTermsService userAgreeTermsService;

    @MockBean
    private UserProfileImageService userProfileImageService;

    @Test
    void ?????????_??????_????????????() throws Exception {
        // Given
        final UserInfoDTO userInfoDTO = initUserInfoDTO(1L, UserType.CLIENT);
        given(userService.signUpClientUser(any(UserInfoDTO.class), anyString(), anyString())).willReturn(userInfoDTO);

        // When
        final SignUpPayload.Request request = SignUpPayload.Request.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .passwordCheck(PASSWORD)
            .nickname(NICKNAME)
            .phone(PHONE)
            .build();
        final ResultActions result = mockMvc.perform(
            post("/user/client")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        result.andExpect(status().isOk())
            .andDo(document("user/sign-up-client",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("????????????"),
                    fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("???????????? ??????"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("phone").type(JsonFieldType.STRING).description("?????????").optional()
                ),
                responseFields(
                    defaultResponseFields()
                )
            ));
    }

    @Test
    void ?????????_??????_????????????() throws Exception {
        // Given
        final UserInfoDTO userInfoDTO = initUserInfoDTO(1L, UserType.BUSINESS);
        given(userService.signUpBusinessUser(any(UserInfoDTO.class), anyString(), anyString())).willReturn(userInfoDTO);

        // When
        final SignUpPayload.Request request = SignUpPayload.Request.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .passwordCheck(PASSWORD)
            .nickname(NICKNAME)
            .phone(PHONE)
            .build();
        final ResultActions result = mockMvc.perform(
            post("/user/business")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        result.andExpect(status().isOk())
            .andDo(document("user/sign-up-business",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("????????????"),
                    fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("???????????? ??????"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("phone").type(JsonFieldType.STRING).description("?????????").optional()
                ),
                responseFields(
                    defaultResponseFields()
                )
            ));
    }

    @Test
    @WithMockCustomUser
    void ??????_??????_??????() throws Exception {
        // Given
        final UserInfoDTO userInfoDTO = initUserInfoDTO(1L, UserType.CLIENT);
        given(userService.getUserInfo(anyLong())).willReturn(userInfoDTO);

        // When
        final ResultActions result = mockMvc.perform(
                get("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("user/info",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                dataResponseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("userType").type(JsonFieldType.STRING).description("?????? ??????").attributes(userTypeAttribute()),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("phone").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                        fieldWithPath("withdrawal").type(JsonFieldType.BOOLEAN).description("?????? ????????????"),
                                        fieldWithPath("profileImage").type(JsonFieldType.OBJECT).description("????????? ????????? ??????").optional(),
                                        fieldWithPath("profileImage.id").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("profileImage.originName").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("profileImage.uploadName").type(JsonFieldType.STRING).description("????????? ?????????"),
                                        fieldWithPath("profileImage.fullPath").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("profileImage.path").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("profileImage.size").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("profileImage.ext").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("useAgreeTerms").type(JsonFieldType.OBJECT).description("?????? ?????? ??????").optional(),
                                        fieldWithPath("useAgreeTerms.id").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ????????????"),
                                        fieldWithPath("useAgreeTerms.agree").type(JsonFieldType.BOOLEAN).description("?????? ?????? ?????? ????????????"),
                                        fieldWithPath("useAgreeTerms.created").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????"),
                                        fieldWithPath("privacyPolicyAgreeTerms").type(JsonFieldType.OBJECT).description("???????????? ???????????? ??????").optional(),
                                        fieldWithPath("privacyPolicyAgreeTerms.id").type(JsonFieldType.NUMBER).description("???????????? ???????????? ????????????"),
                                        fieldWithPath("privacyPolicyAgreeTerms.agree").type(JsonFieldType.BOOLEAN).description("???????????? ???????????? ????????????"),
                                        fieldWithPath("privacyPolicyAgreeTerms.created").type(JsonFieldType.STRING).description("???????????? ???????????? ????????????"),
                                        fieldWithPath("created").type(JsonFieldType.STRING).description("?????? ????????????")
                                )
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void ??????_??????_??????() throws Exception {
        final UserInfoDTO userInfoDTO = initUserInfoDTO(1L, UserType.BUSINESS);
        given(userService.updateUserInfo(anyLong(), any(UserInfoDTO.class))).willReturn(userInfoDTO);

        final UpdateUserInfoPayload.Request request = UpdateUserInfoPayload.Request.builder()
                .nickname("?????????2")
                .phone("01022222222")
                .build();
        final ResultActions result = mockMvc.perform(
                put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
                .andDo(document("user/update-info",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("phone").type(JsonFieldType.STRING).description("?????????").optional()
                        ),
                        responseFields(
                                dataResponseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("userType").type(JsonFieldType.STRING).description("?????? ??????").attributes(userTypeAttribute()),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("phone").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                        fieldWithPath("withdrawal").type(JsonFieldType.BOOLEAN).description("?????? ????????????"),
                                        fieldWithPath("profileImage").type(JsonFieldType.OBJECT).description("????????? ????????? ??????").optional(),
                                        fieldWithPath("profileImage.id").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                        fieldWithPath("profileImage.originName").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("profileImage.uploadName").type(JsonFieldType.STRING).description("????????? ?????????"),
                                        fieldWithPath("profileImage.fullPath").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("profileImage.path").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("profileImage.size").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("profileImage.ext").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("useAgreeTerms").type(JsonFieldType.OBJECT).description("?????? ?????? ??????").optional(),
                                        fieldWithPath("useAgreeTerms.id").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ????????????"),
                                        fieldWithPath("useAgreeTerms.agree").type(JsonFieldType.BOOLEAN).description("?????? ?????? ?????? ????????????"),
                                        fieldWithPath("useAgreeTerms.created").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????"),
                                        fieldWithPath("privacyPolicyAgreeTerms").type(JsonFieldType.OBJECT).description("???????????? ???????????? ??????").optional(),
                                        fieldWithPath("privacyPolicyAgreeTerms.id").type(JsonFieldType.NUMBER).description("???????????? ???????????? ????????????"),
                                        fieldWithPath("privacyPolicyAgreeTerms.agree").type(JsonFieldType.BOOLEAN).description("???????????? ???????????? ????????????"),
                                        fieldWithPath("privacyPolicyAgreeTerms.created").type(JsonFieldType.STRING).description("???????????? ???????????? ????????????"),
                                        fieldWithPath("created").type(JsonFieldType.STRING).description("?????? ????????????")
                                )
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void ??????_??????() throws Exception {
        willDoNothing().given(userService).withdrawUser(anyLong());

        final ResultActions result = mockMvc.perform(
                delete("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
                .andDo(document("user/withdraw",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void ??????_??????() throws Exception {
        willDoNothing().given(userAgreeTermsService).agreeTerms(anyLong(), anyMap());

        final Map<TermsType, Boolean> request = new HashMap<>(){{ put(TermsType.USE, true); put(TermsType.PRIVACY_POLICY, true); }};
        final ResultActions result = mockMvc.perform(
            post("/user/agree-terms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
                .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
            .andDo(document("user/agree-terms",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath(String.valueOf(TermsType.USE)).type(JsonFieldType.BOOLEAN).description(TermsType.USE.getName() + " ?????? ??????").optional(),
                    fieldWithPath(String.valueOf(TermsType.PRIVACY_POLICY)).type(JsonFieldType.BOOLEAN).description(TermsType.PRIVACY_POLICY.getName() + " ?????? ??????").optional()
                ),
                responseFields(
                    defaultResponseFields()
                )
            ));
    }

    @Test
    @WithMockCustomUser
    void ?????????_?????????_??????() throws Exception {
        willDoNothing().given(userProfileImageService).updateProfileImage(anyLong(), any(MultipartFile.class));

        final MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "profileImage.jpg",
                "image/jpg",
                "uploadFile".getBytes());
        final ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.fileUpload("/user/profile-image")
                        .file(multipartFile)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("user/profile-image",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParts(
                                partWithName("file").description("?????? ?????????")
                        ),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void ?????????_?????????_??????() throws Exception {
        willDoNothing().given(userProfileImageService).deleteProfileImage(anyLong());

        final ResultActions result = mockMvc.perform(
                delete("/user/profile-image")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        result.andExpect(status().isOk())
                .andDo(document("user/profile-image-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

}