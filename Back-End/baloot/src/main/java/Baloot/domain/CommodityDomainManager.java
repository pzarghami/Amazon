package Baloot.domain;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;
import Baloot.model.DTO.CommodityBriefDTO;
import Baloot.model.DTO.CommodityDTO;
import Baloot.repository.CommodityRepo;
import Baloot.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

public class CommodityDomainManager {
    private static CommodityDomainManager instance;

    public static CommodityDomainManager getInstance(){
        if (instance == null) {
            instance = new CommodityDomainManager();
        }
        return instance;
    }
    public List<CommodityBriefDTO> getCommodityDTOList() throws CustomException {
        var commodities = CommodityRepo.getInstance().getElementsById(null);
        List<CommodityBriefDTO> commoditiesDTO = new ArrayList<>();
        commodities.forEach(commodity -> commoditiesDTO.add(commodity.getBriefDTO()));
        return commoditiesDTO;
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
