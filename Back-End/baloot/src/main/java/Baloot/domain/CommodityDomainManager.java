package Baloot.domain;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;
import Baloot.model.DTO.CommodityBriefDTO;
import Baloot.model.DTO.CommodityDTO;
import Baloot.repository.CommodityRepo;
import Baloot.repository.UserRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommodityDomainManager {
    private static CommodityDomainManager instance;

    public static CommodityDomainManager getInstance() {
        if (instance == null) {
            instance = new CommodityDomainManager();
        }
        return instance;
    }
    private List<CommodityBriefDTO> getCommoditiesDTO(List<Commodity> commodities){
        List<CommodityBriefDTO> commoditiesDTO = new ArrayList<>();
        commodities.forEach(commodity -> commoditiesDTO.add(commodity.getBriefDTO(0)));
        return commoditiesDTO;
    }

    public List<CommodityBriefDTO> getCommodityDTOList() throws CustomException, SQLException {
        var commodities = CommodityRepo.getInstance().getAllElements();
        return getCommoditiesDTO(commodities);
    }

    public CommodityDTO getCommodityDTO(String commodityId) throws CustomException, SQLException {
        var commodity = CommodityRepo.getInstance().getElementById(Integer.valueOf(commodityId));
        return getCommodityDTO(commodity);
    }

    public CommodityDTO rateCommodity(String commodityId, int rate) throws CustomException, SQLException {
        var ratingUser = UserRepo.loggedInUser;
        var commodity = CommodityRepo.getInstance().getElementById(Integer.valueOf(commodityId));
        commodity.updateCommodityRating(ratingUser.getUsername(), rate);
        return getCommodityDTO(commodityId);
    }

    private CommodityDTO getCommodityDTO(Commodity commodity) throws CustomException, SQLException {
        CommodityDTO DTO;
        if (UserRepo.loggedInUser != null) {
            DTO = commodity.getDTO(UserRepo.getQuantityOfCommodity(commodity));
            DTO.setUserRate(UserRepo.getUserRate(commodity));
        } else {
            DTO = commodity.getDTO(0);
            DTO.setUserRate(0);
        }
        return DTO;

    }

    public List<CommodityBriefDTO> getFilteredCommodityByName(String filterValue) throws SQLException {
        ArrayList<Commodity> commodities=CommodityRepo.getInstance().getFilteredElementsByName(filterValue);
        return getCommoditiesDTO(commodities);
    }

    public List<CommodityBriefDTO> getFilteredCommodityByCategory(String filterValue) throws SQLException {
        ArrayList<Commodity> commodities=CommodityRepo.getInstance().getFilteredElementsByCategory(filterValue);
        return getCommoditiesDTO(commodities);
    }

    public List<CommodityBriefDTO> getFilteredCommodityByProvider(String filterValue
    ) throws SQLException {
        ArrayList<Commodity> commodities=CommodityRepo.getInstance().getFilteredElementsByProvider(filterValue);
        return getCommoditiesDTO(commodities);
    }
}
