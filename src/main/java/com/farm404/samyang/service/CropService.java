package com.farm404.samyang.service;

import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.mapper.CropMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropService {
    
    @Autowired
    private CropMapper cropMapper;
    
    public CropDTO getCropById(Integer cropId) {
        return cropMapper.selectCropById(cropId);
    }
    
    public List<CropDTO> getCropsByUserId(Integer userId) {
        return cropMapper.selectCropsByUserId(userId);
    }
    
    public List<CropDTO> getAllCrops() {
        return cropMapper.selectAllCrops();
    }
    
    public List<CropDTO> searchCrops(String keyword) {
        return cropMapper.searchCrops(keyword);
    }
    
    public boolean registerCrop(CropDTO crop) {
        return cropMapper.insertCrop(crop) > 0;
    }
    
    public boolean updateCrop(CropDTO crop) {
        return cropMapper.updateCrop(crop) > 0;
    }
    
    public boolean deleteCrop(Integer cropId) {
        return cropMapper.deleteCrop(cropId) > 0;
    }
    
    public List<CropDTO> getCropsByStatus(String status) {
        return cropMapper.selectCropsByStatus(status);
    }
}