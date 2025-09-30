package LearningSpring.Security.service;


import LearningSpring.Security.entity.SessionEntity;
import LearningSpring.Security.entity.SessionLimit;
import LearningSpring.Security.entity.UserEntity;
import LearningSpring.Security.repo.SessionRepo;
import LearningSpring.Security.repo.Session_LimitRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepo sessionRepo;

    private final Session_LimitRepo sessionLimitRepo;


    public void generateNewSession(UserEntity user,String refreshtoken){

        List<SessionEntity> sessionEntities=sessionRepo.findByUser(user);

        SessionLimit sessionLimit=sessionLimitRepo.findByUser(user);

        if(sessionEntities.size()==sessionLimit.getSession_Limit()){
            sessionEntities.sort(Comparator.comparing(SessionEntity::getLastUsedAT));
            SessionEntity sessionEntity = sessionEntities.get(0);
            sessionRepo.delete(sessionEntity);
        }

        SessionEntity sessionEntity=SessionEntity.builder()
                .user(user)
                .refreshtoken(refreshtoken)
                 .build();

        sessionRepo.save(sessionEntity);
    }

    public void validateSession(String refreshToken){
        SessionEntity sessionEntity=sessionRepo.findByrefreshtoken(refreshToken).orElseThrow(()->new SessionAuthenticationException("Session With Refersh token Not Found "+refreshToken));
        sessionEntity.setLastUsedAT(LocalDateTime.now());
        sessionRepo.save(sessionEntity);
    }

    public void logout(String refreshToken) {
        SessionEntity sessionEntity=sessionRepo.findByrefreshtoken(refreshToken).orElseThrow(()->new SessionAuthenticationException("Session With Refersh token Not Found "+refreshToken));
        sessionRepo.delete(sessionEntity);
    }
}
