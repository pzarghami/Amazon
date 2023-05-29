package Baloot.domain;

import Baloot.Exeption.CustomException;
import Baloot.model.DTO.ProviderDTO;
import Baloot.repository.ProviderRepo;

import java.sql.SQLException;

public class ProviderDomainManager {
    private static ProviderDomainManager instance;
    public static ProviderDomainManager getInstance(){
        if (instance == null) {
            instance = new ProviderDomainManager();
        }
        return instance;
    }
    public ProviderDTO getProviderDTO(String providerID) throws CustomException, SQLException {
        return ProviderRepo.getInstance().getElementById(Integer.valueOf(providerID)).getDTO();
    }
}
