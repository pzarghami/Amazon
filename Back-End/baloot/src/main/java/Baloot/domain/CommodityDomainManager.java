package Baloot.domain;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;
import Baloot.model.DTO.CommodityDTO;
import Baloot.repository.CommodityRepo;
import Baloot.repository.UserRepo;

public class CommodityDomainManager {
    private static CommodityDomainManager instance;

    public static CommodityDomainManager getInstance(){
        if (instance == null) {
            instance = new CommodityDomainManager();
        }
        return instance;
    }

    public CommodityDTO getCommodityDTO(String commodityId)throws CustomException{
        var commodity = CommodityRepo.getInstance().getElementById(commodityId);
        return getCommodityDTO(commodity);
    }
    public CommodityDTO rateCommodity(String commodityId, int rate)throws CustomException{
        var ratingUser = UserRepo.loggedInUser;
        var commodity = CommodityRepo.getInstance().getElementById(commodityId);
        commodity.updateCommodityRating(ratingUser.getUsername(),rate);
        return getCommodityDTO(commodityId);
    }
    private CommodityDTO getCommodityDTO(Commodity commodity){
        var DTO = commodity.getDTO();
        if(UserRepo.loggedInUser != null)
            DTO.setUserRate(commodity.getUserRate(UserRepo.loggedInUser.getUsername()));
        return DTO;
    }

}
