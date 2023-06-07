package Baloot.model;

import Baloot.model.DTO.CommodityBriefDTO;
import Baloot.model.DTO.ProviderDTO;

import java.util.ArrayList;
import java.util.Map;

public class Provider {
    private Integer id;
    private String name;
    private String registryDate;
    private String image;
    private ArrayList<Commodity> commoditiesList;

    public Provider(int id, String name, String registryDate, String image) {
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
        this.image = image;
        this.commoditiesList = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void addCommodity(Commodity commodity) {
        commoditiesList.add(commodity);
    }

    public ProviderDTO getDTO() {
        var providerDTO = new ProviderDTO();
        providerDTO.setId(this.id);
        providerDTO.setName(this.name);
        providerDTO.setRegistryDate(this.registryDate);
        providerDTO.setImage(image);
        var providersCommodityDTO = new ArrayList<CommodityBriefDTO>();
        commoditiesList.forEach(commodity -> providersCommodityDTO.add(commodity.getBriefDTO(0)));
        providerDTO.setCommoditiesList(providersCommodityDTO);
        return providerDTO;
    }

    public Map<String, String> getDBTuple() {
        return Map.of("id", id.toString(), "name", name, "registryDate", registryDate, "imgUrl", image);
    }

}
