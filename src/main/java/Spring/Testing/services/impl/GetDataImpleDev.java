package Spring.Testing.services.impl;

import Spring.Testing.services.GetDataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Service
@Profile("dev")
public class GetDataImpleDev implements GetDataService {
    @Override
    public String getData() {
        return "This is Dev Environment";
    }
}
