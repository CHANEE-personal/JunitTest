package com.example.study;

import com.example.domain.Member;
import com.example.domain.Study;
import com.example.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock MemberService memberService;
    @Mock StudyRepository studyRepository;

    @Test
    void createNewStudy() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("cksgml@email.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

//        when(memberService.findById(any()))
//                .thenReturn(Optional.of(member))
//                .thenThrow(new RuntimeException())
//                .thenReturn(Optional.empty());
//
//        Optional<Member> byId = memberService.findById(1L);
//        assertEquals("cksgml@email.com", byId.get().getEmail());
//
//        assertThrows(RuntimeException.class, () -> {
//           memberService.findById(2L);
//        });
//
//        assertEquals(Optional.empty(), memberService.findById(3L));

//        assertEquals("cksgml@email.com", memberService.findById(1L).get().getEmail());
//        assertEquals("cksgml@email.com", memberService.findById(2L).get().getEmail());
//
//        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            memberService.validate(1L);
//        });
//
//        memberService.validate(2L);
        studyService.createNewStudy(1L, study);

        verify(memberService, times(1)).notify(study);
        verifyNoMoreInteractions(memberService);

        verify(memberService, never()).validate(any());

//        InOrder inOrder = inOrder(memberService);
//        inOrder.verify(memberService).notify(study);

    }
}