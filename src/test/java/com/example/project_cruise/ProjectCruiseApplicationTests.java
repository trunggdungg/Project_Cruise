package com.example.project_cruise;

import com.example.project_cruise.entity.*;
import com.example.project_cruise.model.User_Role;
import com.example.project_cruise.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringBootTest
class ProjectCruiseApplicationTests {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CabinRepository cabinRepository;

    @Autowired
    private CabinTypeImageRepository cabinTypeImageRepository;

    @Autowired
    private CabinTypeRepository cabinTypeRepository;

    @Autowired
    private CruiseImageRepository cruiseImageRepository;

    @Autowired
    private CruiseRepository cruiseRepository;

    @Autowired
    private DescriptionShortRepository descriptionShortRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private OwnedRepository ownedRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    void save_users() {
        Faker faker = new Faker();
        for (int i = 0; i < 50; i++) {
            String name = faker.leagueOfLegends().champion();
            User user = User.builder()
                .fullName(name)
                .role(i == 0 || i == 1 ? User_Role.ADMIN : User_Role.USER)
                .phone(faker.phoneNumber().cellPhone())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .address(faker.address().fullAddress())
                .avatar("https://placehold.co/200x200?text=" + name.substring(0, 1).toUpperCase())
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();
            userRepository.save(user);

        }
    }

    @Test
    void save_owned() {
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            Owned owned = Owned.builder()
                .name(faker.name().fullName())
                .build();
            ownedRepository.save(owned);
        }
    }

    @Test
    void save_location() {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            Location location = Location.builder()
                .routeName(faker.address().streetName())
                .address(faker.address().fullAddress())
                .city(faker.address().city())
                .build();
            locationRepository.save(location);
        }
    }

    @Test
    void save_rule() {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            Rule rule = Rule.builder()
                .ruleDescription(faker.lorem().sentence())
                .build();
            ruleRepository.save(rule);
        }
    }

    @Test
    void save_tag() {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            Tag tag = Tag.builder()
                .icon("https://placehold.co/200x200?text=" + faker.name().firstName().substring(0, 1).toUpperCase())
                .name(faker.name().lastName())
                .type(faker.name().title())
                .build();
            tagRepository.save(tag);
        }
    }

    @Test
    void save_cruise() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();

        Random random = new Random();
        List<Location> locations = locationRepository.findAll();
        List<Owned> owneds = ownedRepository.findAll();
        List<Tag> tags = tagRepository.findAll();
        List<Rule> rules = ruleRepository.findAll();
        for (int i = 0; i < 50; i++) {
            String nameCruise = faker.name().firstName();
            String slug = slugify.slugify(nameCruise);

            // Kiểm tra slug đã tồn tại trong cơ sở dữ liệu
            while (cruiseRepository.existsBySlug(slug)) {
                slug = slugify.slugify(nameCruise + random.nextInt(1000)); // Thêm số ngẫu nhiên để tạo slug duy nhất
            }

            List<Tag> randomTags = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3) + 1; j++) {
                Tag randomTag = tags.get(random.nextInt(tags.size()));
                if (!randomTags.contains(randomTag)) {
                    randomTags.add(randomTag);
                }
            }

            List<Rule> randomRules = new ArrayList<>();
            for (int j = 0; j < random.nextInt(6) + 1; j++) {
                Rule randomRule = rules.get(random.nextInt(rules.size()));
                if (!randomRules.contains(randomRule)) {
                    randomRules.add(randomRule);
                }
            }
            // Chọn một location ngẫu nhiên
            Location randomLocation = locations.get(random.nextInt(locations.size()));
            Owned randomOwned = owneds.get(random.nextInt(owneds.size()));
            Cruise cruise = Cruise.builder()
                .name(nameCruise)
                .slug(slug)
                .launchedYear(faker.number().numberBetween(2000, 2024))
                .cabinQuantity(faker.number().numberBetween(10, 20))
                .material(faker.food().ingredient())
                .description(faker.lorem().paragraph())
                .price(BigDecimal.valueOf(faker.number().randomNumber(2, true)))
                .departureTime(LocalTime.now())
                .arrivalTime(LocalTime.now().plusHours(12))
                .location(randomLocation)
                .created_at(LocalDateTime.now())
                .owned(randomOwned)
                .updated_at(LocalDateTime.now())
                .tags(randomTags)
                .rules(randomRules)
                .build();
            cruiseRepository.save(cruise);
        }
    }


    @Test
    void save_blog() {
        Faker faker = new Faker();
        Random random = new Random();
        List<User> users = userRepository.findAll();

        // Lọc ra danh sách người dùng có vai trò ADMIN
        List<User> adminUsers = users.stream()
            .filter(user -> user.getRole() == User_Role.ADMIN)
            .collect(Collectors.toList());

        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 50; i++) {
            String title = faker.name().fullName();

            // Chọn ngẫu nhiên một admin
            User randomAdmin = adminUsers.get(random.nextInt(adminUsers.size()));

            Blog blog = Blog.builder()
                .title(title)
                .slug(slugify.slugify(title))
                .content(faker.lorem().paragraph())
                .description(faker.lorem().sentence())
                .thumbnail("https://placehold.co/200x200?text=" + title.substring(0, 1).toUpperCase())
                .status(random.nextBoolean())
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .published_at(LocalDateTime.now())
                .user(randomAdmin)
                .build();
            blogRepository.save(blog);
        }
    }

    @Test
    void save_cabin_type() {
        Faker faker = new Faker();
        Random random = new Random();
        List<Tag> tags = tagRepository.findAll();

        for (int i = 0; i < 10; i++) {
            List<Tag> randomTags = new ArrayList<>();
            for (int j = 0; j < random.nextInt(5) + 1; j++) {
                Tag randomTag = tags.get(random.nextInt(tags.size()));
                if (!randomTags.contains(randomTag)) {
                    randomTags.add(randomTag);
                }
            }

            CabinType cabinType = CabinType.builder()
                .name(faker.leagueOfLegends().rank())
                .roomSize(faker.number().numberBetween(20, 45))
                .numberGuests(faker.number().numberBetween(1, 4))
                .price(BigDecimal.valueOf(faker.number().randomNumber(2, true)))
                .tags(randomTags)
                .build();
            cabinTypeRepository.save(cabinType);
        }
    }

    @Test
    void save_cabin_type_image() {
        Faker faker = new Faker();
        Random random = new Random();

        // Lấy danh sách các cabin types từ repository
        List<CabinType> cabinTypes = cabinTypeRepository.findAll();

        for (int i = 0; i < 20; i++) {
            // Chọn ngẫu nhiên một cabinType từ danh sách
            CabinType randomCabinType = cabinTypes.get(random.nextInt(cabinTypes.size()));

            CabinTypeImage cabinTypeImage = CabinTypeImage.builder()
                .type(faker.name().title())
                .urlImage("https://placehold.co/200x200?text=" + faker.name().firstName().substring(0, 1).toUpperCase())
                .created_at(LocalDateTime.now())
                .cabinType(randomCabinType) // Sử dụng cabinType đã chọn ngẫu nhiên
                .build();

            cabinTypeImageRepository.save(cabinTypeImage);
        }
    }

    @Test
    void save_cruise_img() {
        Faker faker = new Faker();
        Random random = new Random();
        List<Cruise> cruises = cruiseRepository.findAll();
        for (int i = 0; i < 20; i++) {
            Cruise cruise = cruises.get(random.nextInt(cruises.size()));
            CruiseImage cruiseImage = CruiseImage.builder()
                .type(faker.name().title())
                .urlImage("https://placehold.co/200x200?text=" + faker.name().firstName().substring(0, 1).toUpperCase())
                .created_at(LocalDateTime.now())
                .cruise(cruise)
                .build();
            cruiseImageRepository.save(cruiseImage);
        }
    }

    @Test
    void save_decrtiption_short() {
        Faker faker = new Faker();
        Random random = new Random();
        List<Cruise> cruises = cruiseRepository.findAll();
        for (int i = 0; i < 20; i++) {
            Cruise cruise = cruises.get(random.nextInt(cruises.size()));
            DescriptionShort descriptionShort = DescriptionShort.builder()
                .cruise(cruise)
                .shortDescription(faker.lorem().sentence())
                .build();
            descriptionShortRepository.save(descriptionShort);
        }
    }

    @Test
    void save_schedule() {
        Faker faker = new Faker();
        Random random = new Random();
        List<Cruise> cruises = cruiseRepository.findAll();
        for (int i = 0; i < 20; i++) {
            Cruise cruise = cruises.get(random.nextInt(cruises.size()));
            // Tạo URL ngẫu nhiên sử dụng Faker
            String filePath = "https://example.com/files/" + faker.file().fileName();
            Schedule schedule = Schedule.builder()
                .cruise(cruise)
                .filePath(filePath)
                .description(faker.lorem().sentence())
                .uploadDate(LocalDateTime.now())
                .build();
            scheduleRepository.save(schedule);
        }
    }

    @Test
    void save_cabin() {
        Faker faker = new Faker();
        Random random = new Random();
        List<Cruise> cruises = cruiseRepository.findAll();
        List<CabinType> cabinTypes = cabinTypeRepository.findAll();

        List<Integer> roomNumbers = Arrays.asList(101, 102, 103, 104, 105, 106);
        // Lấy một giá trị ngẫu nhiên từ list
        int randomRoomNumber = roomNumbers.get(random.nextInt(roomNumbers.size()));
        for (int i = 0; i < 20; i++) {
            Cruise cruise = cruises.get(random.nextInt(cruises.size()));
            CabinType cabinType = cabinTypes.get(random.nextInt(cabinTypes.size()));
            Cabin cabin = Cabin.builder()
                .roomNumber(randomRoomNumber)
                .cruise(cruise)
                .cabinType(cabinType)
                .floor(faker.number().numberBetween(1, 3))
                .build();
            cabinRepository.save(cabin);

        }
    }

    @Test
    void save_booking() {
        Faker faker = new Faker();
        Random random = new Random();
        List<User> users = userRepository.findAll();
        List<Cruise> cruises = cruiseRepository.findAll();
        List<Cabin> cabins = cabinRepository.findAll();
        for (int i = 0; i < 20; i++) {
            User user = users.get(random.nextInt(users.size()));
            Cruise cruise = cruises.get(random.nextInt(cruises.size()));
            List<Cabin> randomCabins = new ArrayList<>();
            for (int j = 0; j < random.nextInt(5) + 1; j++) {
                Cabin randomCabin = cabins.get(random.nextInt(cabins.size()));
                if (!randomCabins.contains(randomCabin)) {
                    randomCabins.add(randomCabin);
                }
            }
            Booking booking = Booking.builder()
                .bookingDate(LocalDateTime.now())
                .guestQuantity(faker.number().numberBetween(1, 5))
                .totalPrice(BigDecimal.valueOf(faker.number().randomNumber(2, true)))
                .note(faker.lorem().sentence())
                .bookingStatus(random.nextBoolean())
                .created_at(LocalDateTime.now())
                .user(user)
                .cruise(cruise)
                .cabins(randomCabins)
                .build();
            bookingRepository.save(booking);
        }
    }

    @Test
    void save_payment() {
        Faker faker = new Faker();
        Random random = new Random();
        List<Booking> bookings = bookingRepository.findAll();
        for (int i = 0; i < bookings.size(); i++) { // Sử dụng chỉ mục i để lấy lần lượt
            Booking booking = bookings.get(i); // Lấy booking theo thứ tự
            Payment payment = Payment.builder()
                .paymentDate(LocalDateTime.now())
                .paymentMethod(faker.finance().creditCard())
                .paymentStatus(random.nextBoolean()) // Hoặc bất kỳ giá trị boolean nào bạn muốn
                .amount(booking.getTotalPrice())
                .booking(booking)
                .build();
            paymentRepository.save(payment);
        }
    }


    @Test
    void save_review() {
        Faker faker = new Faker();
        Random random = new Random();
        List<User> users = userRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();
        for (int i = 0; i < 20; i++) {
            User user = users.get(random.nextInt(users.size()));
            Booking booking = bookings.get(random.nextInt(bookings.size()));
            Review review = Review.builder()
                .content(faker.lorem().paragraph())
                .rating(faker.number().numberBetween(1, 5))
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .user(user)
                .booking(booking)
                .build();
            reviewRepository.save(review);
        }
    }


}
