package Baloot.domain;

import Baloot.Exeption.CustomException;
import Baloot.model.DTO.ProviderDTO;
import Baloot.repository.ProviderRepo;

public class ProviderDomainManager {
    private static ProviderDomainManager instance;
    public static ProviderDomainManager getInstance(){
        if (instance == null) {
            instance = new ProviderDomainManager();
        }
        return instance;
    }
    public ProviderDTO getProviderDTO(String providerID)throws CustomException{
        return ProviderRepo.getInstance().getElementById(providerID).getDTO();
    }
}
