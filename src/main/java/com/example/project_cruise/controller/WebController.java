package com.example.project_cruise.controller;

import com.example.project_cruise.entity.Cruise;
import com.example.project_cruise.entity.CruiseImage;
import com.example.project_cruise.entity.Tag;
import com.example.project_cruise.service.CruiseImageService;
import com.example.project_cruise.service.CruiseService;
import com.example.project_cruise.service.LocationService;
import com.example.project_cruise.service.TagService;
import com.example.project_cruise.specification.CruiseSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final CruiseService cruiseService;
    private final CruiseImageService cruiseImageService;
    private final TagService tagService;
    private final LocationService locationService;

    @GetMapping("/")
    public String getHomePage() {
        return "/fragment/web/index";
    }

    @GetMapping("/search-cruise")
    public String getSearchCruisePage(Model model,
                                      @RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "5") int size,
                                      @RequestParam(required = false) String title,
                                      @RequestParam(required = false) String location,
                                      @RequestParam(required = false) Double fromPrice,
                                      @RequestParam(required = false) Double toPrice) {
        // Lấy danh sách cruise với phân trang
        Page<Cruise> pageCruises = cruiseService.getCruisesWithFilters(title, location, fromPrice, toPrice, page, size);
//        Page<Cruise> pageCruises = cruiseService.getAllCruises(page, size);

        // Lấy danh sách cruiseIds từ trang hiện tại
        List<Integer> cruiseIds = pageCruises.getContent().stream()
            .map(Cruise::getId)
            .collect(Collectors.toList());

        Map<Integer, String> cruiseImages = null;
        CruiseImage cruiseImage = null;
        // Kiểm tra nếu danh sách cruiseIds không rỗng
        if (!cruiseIds.isEmpty()) {
            // Lấy danh sách ảnh của các du thuyền hiện tại
            cruiseImages = cruiseImageService.getAllCruiseImages();

            // Lấy ảnh của du thuyền đầu tiên trong danh sách
            cruiseImage = cruiseService.findCruiseImageByCruiseId(cruiseIds.get(0));
            // Xử lý với cruiseImage
        } else {
            // Xử lý trường hợp không có du thuyền nào trong danh sách
            System.out.println("Không có du thuyền nào được tìm thấy.");
        }

        // Lấy tag theo type
        List<Tag> tags = tagService.getTagByType();

        //Lay locationName
        List<String> locationNames = locationService.getAllLocationName();
        // Thêm dữ liệu vào model để hiển thị trên giao diện
        model.addAttribute("locationNames", locationNames);
        model.addAttribute("tags", tags);
        model.addAttribute("cruiseImg", cruiseImage);
        model.addAttribute("cruiseImagesMap", cruiseImages);
        model.addAttribute("pageCruises", pageCruises);
        model.addAttribute("currentPage", page);
        return "/fragment/web/search-cruise";
    }

    @GetMapping("/search-cruiseByTag")
    public String getSearchCruiseByTag(@RequestParam("tagIds") List<String> tagIds, Model model) {
        // Gọi đến service để tìm danh sách Cruise dựa trên danh sách tag
        List<Cruise> cruises = cruiseService.findCruisesByTag(tagIds);
        // Thêm danh sách Cruise vào model để hiển thị trong view
        model.addAttribute("cruises", cruises);
        // Trả về view (template) của kết quả tìm kiếm
        return "/fragment/web/search-cruise";
    }


    @GetMapping("/blog")
    public String getBlogPage() {
        return "/fragment/web/blog";
    }
}
