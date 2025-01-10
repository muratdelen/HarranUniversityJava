package com.harran.oturum.init;

import com.harran.oturum.dao.authority.*;
import com.harran.oturum.model.authority.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import java.util.concurrent.CompletableFuture;
/*
İlk yükleme sırasında veritabanına yüklenecek veriler.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepository;
    private final ApplicationRepo applicationRepository;
    private final MenuRepo menuRepository;
    private final PageUrlRepo pageUrlRepository;
    private final GroupRepo groupRepository;
    private final PermissionRepo permissionRepository;
    private final RoleRepo roleRepository;
    private final RolePermissionRepo rolePermissionRepository;
    private final UserGroupRepo userGroupRepository;
    private final GroupRoleRepo groupRoleRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final List<Group> groups = Arrays.asList(
            new Group("Developer","Geliştirici grubu"),
            new Group("SuperAdmin","Geliştirici dışında tüm yetkilere sahip grup(Loglama tam yetkili erişim)"),
            new Group("DefaultAdmin","Geliştirici ve Loglama dışında tüm yetkilere erişebilen grup"),
            new Group("Admin","Silme işlemi için yetkili olmayan grup")
    );
    private final List<Permission> permissions = Arrays.asList(
            new Permission("CONTENT_CREATE", "İçerik oluşturma"),
            new Permission("CONTENT_READ", "İçerik okuma"),
            new Permission("CONTENT_EDIT", "İçeriği düzenleme"),
            new Permission("CONTENT_DELETE", "İçeriği silme"),
            new Permission("CONTENT_APPROVE", "İçeriği onaylama"),
            new Permission("CONTENT_PUBLISH", "İçeriği yayına alma"),
            new Permission("CONTENT_ARCHIVE", "İçeriği arşivleme"),
            new Permission("EVENT_CREATE", "Etkinlik oluşturma"),
            new Permission("EVENT_READ", "Etkinlik okuma"),
            new Permission("EVENT_EDIT", "Etkinlik düzenleme"),
            new Permission("EVENT_DELETE", "Etkinlik silme"),
            new Permission("EVENT_PARTICIPANT_VIEW", "Katılımcıları görüntüleme"),
            new Permission("EVENT_PARTICIPANT_MANAGE", "Katılımcıları yönetme"),
            new Permission("USER_CREATE", "Kullanıcı oluşturma"),
            new Permission("USER_READ", "Kullanıcı okuma"),
            new Permission("USER_EDIT", "Kullanıcı düzenleme"),
            new Permission("USER_DELETE", "Kullanıcı silme"),
            new Permission("USER_GROUP_ASSIGN", "Kullanıcıya grup atama"),
            new Permission("USER_GROUP_MANAGE", "Kullanıcı grubunu yönetme"),
            new Permission("SYSTEM_SECURITY_VIEW", "Sistem güvenliğini görüntüleme"),
            new Permission("SYSTEM_SECURITY_EDIT", "Sistem güvenliğini düzenleme"),
            new Permission("ACCESS_LOG_VIEW", "Erişim günlüklerini görüntüleme"),
            new Permission("ACCESS_LOG_DELETE", "Erişim günlüklerini silme"),
            new Permission("API_INTEGRATION_CREATE", "API entegrasyonu oluşturma"),
            new Permission("API_INTEGRATION_READ", "API entegrasyonu okuma"),
            new Permission("API_INTEGRATION_EDIT", "API entegrasyonu düzenleme"),
            new Permission("API_INTEGRATION_DELETE", "API entegrasyonu silme"),
            new Permission("API_ACCESS_LOG_VIEW", "API erişim günlüklerini görüntüleme"),
            new Permission("BACKUP_CREATE", "Yedekleme oluşturma"),
            new Permission("BACKUP_READ", "Yedekleme okuma"),
            new Permission("BACKUP_RESTORE", "Yedekleme geri yükleme"),
            new Permission("BACKUP_VIEW", "Yedekleme görüntüleme"),
            new Permission("SYSTEM_CONFIG_EDIT", "Sistem yapılandırmasını düzenleme"),
            new Permission("SYSTEM_CONFIG_VIEW", "Sistem yapılandırmasını görüntüleme"),
            new Permission("CONFIGURATION_MANAGE", "Yapılandırma yönetimi")
    );
    private final  List<Role> roles = List.of(
            new Role("SUPER_ADMIN", "Tüm sistem üzerinde tam kontrol."),
            new Role("ADMIN", "Belirli bir uygulama veya modül üzerinde tam kontrol."),
            new Role("USER", "Sistemin standart kullanıcı özelliklerine erişim."),
            new Role("GUEST", "Sınırlı erişim; herkese açık verilere erişim."),
            new Role("CONTENT_MANAGER", "İçerik oluşturma ve düzenleme yetkisi."),
            new Role("EDITOR", "Mevcut içerikleri düzenleme yetkisi."),
            new Role("VIEWER", "Sadece içerikleri görüntüleme yetkisi."),
            new Role("SUPPORT_AGENT", "Kullanıcı desteği sağlama yetkisi."),
            new Role("DEVELOPER", "Uygulamayı geliştirme ve hata ayıklama yetkisi."),
            new Role("OPERATOR", "Sistemin operasyonel süreçlerini yönetme yetkisi."),
            new Role("REPORT_VIEWER", "Raporları görüntüleme yetkisi."),
            new Role("REPORT_ANALYST", "Raporları analiz etme ve iş kararları için öneriler sunma."),
            new Role("API_USER", "API uç noktalarına erişim hakkına sahip kullanıcı."),
            new Role("RESOURCE_MANAGER", "Belirli bir kaynağı veya modülü yönetme yetkisi.")
    );

    private final List<User> users = Arrays.asList(
            new User("muratdelen",encoder.encode("1234"),"Murat","DELEN","muratdelen@harran.edu.tr", groups.get(0))
    );
    private final List<Application> applications = Arrays.asList(
            new Application("KaliteYönetimSistemi","açıklama", true, "193.168.1.1","//oturum.harran.edu.tr", users.get(0)),
            new Application("Geribildirim Sistemi", "Teşekkür, Takdir, Eleştiri, Talep, Dilek, İstek, Öneri, Şikayet", true, "193.168.1.1", "//geribildirim.harran.edu.tr", users.get(0)),
            new Application("Seçim Sistemi", "Tüm seçimler buradan yapılmaktadır.", true, "193.168.1.1", "//secim.harran.edu.tr", users.get(0)),
            new Application("Yönetime Katılım Sistemi", "Yönetim kararlarına katılabilirsiniz.", true, "193.168.1.1", "//katilim.harran.edu.tr", users.get(0)),
            new Application("Yönetime İş Takip Sistemi", "Yönetim yapmayı planladığı işlerin takibini sağlar.", true, "193.168.1.1", "//katilim.harran.edu.tr", users.get(0)),
            new Application("Veri Sistemi", "Tüm veri api bilgilerine buradan erişebilirsiniz.", true, "193.168.1.1", "//bigdata.harran.edu.tr", users.get(0)),
            new Application("Veri Takip Sistemi", "Tüm verileri belli peryodlar ile takip edebilirsiniz.", true, "193.168.1.1", "//takip.harran.edu.tr", users.get(0)),
            new Application("Veri Toplama Sistemi", "Tüm verileri buradan toplayabilirsiniz.", true, "193.168.1.1", "//verial.harran.edu.tr", users.get(0)),
            new Application("Veri Gösterme Sistemi", "Tüm verileri buradan yetkiniz dahilinde görebilirsiniz.", true, "193.168.1.1", "//verigoster.harran.edu.tr", users.get(0))
    );
    private final List<Menu> menus = Arrays.asList(
            new Menu(applications.get(0),"Home", "/home", "Home page", "home-icon.png", null, "General", "Link", "Active", "en", true),
            new Menu(applications.get(0),"About Us", "/about", "Learn more about us", "about-icon.png", null, "Information", "Page", "Active", "en", true),
            new Menu(applications.get(0),"Services", "/services", "Our services", "services-icon.png", null, "General", "Dropdown", "Active", "en", true),
            new Menu(applications.get(0),"Contact", "/contact", "Get in touch with us", "contact-icon.png", null, "Information", "Page", "Active", "en", true),
            new Menu(applications.get(0),"Dashboard", "/dashboard", "User dashboard", "dashboard-icon.png", 1L, "Admin", "Page", "Active", "en", false),
            new Menu(applications.get(0),"Settings", "/settings", "User settings", "settings-icon.png", 1L, "Admin", "Page", "Inactive", "en", true),
            new Menu(applications.get(0),"Help", "/help", "Help and support", "help-icon.png", null, "Support", "Page", "Active", "en", true),
            new Menu(applications.get(0),"Logging", "/logging", "System logging", "logging-icon.png", null, "Admin", "Page", "Active", "en", true),
            new Menu(applications.get(0),"Authorization", "/authorization", "Manage user permissions", "authorization-icon.png", 1L, "Admin", "Page", "Active", "en", true),
            new Menu(applications.get(0),"Anasayfa", "/anasayfa", "Ana sayfa", "anasayfa-icon.png", null, "Genel", "Bağlantı", "Aktif", "tr", true),
            new Menu(applications.get(0),"Hakkımızda", "/hakkimizda", "Hakkımızda daha fazla bilgi alın", "hakkimizda-icon.png", null, "Bilgi", "Sayfa", "Aktif", "tr", true),
            new Menu(applications.get(0),"Hizmetlerimiz", "/hizmetlerimiz", "Hizmetlerimiz hakkında bilgi", "hizmetler-icon.png", null, "Genel", "Açılır Menü", "Aktif", "tr", true),
            new Menu(applications.get(0),"İletişim", "/iletisim", "Bizimle iletişime geçin", "iletisim-icon.png", null, "Bilgi", "Sayfa", "Aktif", "tr", true),
            new Menu(applications.get(0),"Loglama", "/loglama", "Sistem loglarını görüntüleme", "loglama-icon.png", null, "Yönetim", "Sayfa", "Aktif", "tr", true),
            new Menu(applications.get(0),"Yetkilendirme", "/yetkilendirme", "Kullanıcı izinlerini yönetme", "yetkilendirme-icon.png", 1L, "Yönetim", "Sayfa", "Aktif", "tr", true)
    );
    private final List<PageUrl> pageUrls = Arrays.asList(
            new PageUrl("Kalite Anasayfası", "HU Kalite uygulaması anasayfası", applications.get(0), menus.get(0),"kalite","GET"),
            new PageUrl("Geri Bildirim Anasayfası", "HU Geri Bildirim uygulaması anasayfası", applications.get(0), menus.get(0),"/","GET")
    );
    private final List<RolePermission> rolePermissions = List.of(
            new RolePermission("Role yetkisi","açıklama",applications.get(0), roles.get(0), pageUrls.get(0), permissions.get(0))
    );
    private final List<UserGroup> userGroups = Arrays.asList(
            new UserGroup("ikinci Grubu","1. birden fazla grup eklenebilir",users.get(0),groups.get(1)),
            new UserGroup("Üçüncü Grubu","2. birden fazla grup eklenebilir",users.get(0),groups.get(2))
    );
    private final List<GroupRole> groupRoles = Arrays.asList(
            new GroupRole("1. grup rolü","açıklama",groups.get(0),roles.get(0))
    );

    public DataInitializer(UserRepo userRepository, ApplicationRepo applicationRepository, MenuRepo menuRepository, PageUrlRepo pageUrlRepository, GroupRepo groupRepository, PermissionRepo permissionRepository, RoleRepo roleRepository, RolePermissionRepo rolePermissionRepository, UserGroupRepo userGroupRepository, UserGroupRepo groupGroupRepository, GroupRoleRepo groupRoleRepository) {
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.menuRepository = menuRepository;
        this.pageUrlRepository = pageUrlRepository;
        this.groupRepository = groupRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.userGroupRepository = userGroupRepository;
        this.groupRoleRepository = groupRoleRepository;
    }
    /*
    asenktron fonksiyonlar tanımlanıyor. bu fonksiyon içerisinde tanımlananlar işlem bitene kadar uygulama bekletiliyor.
     */
    @Async
    public CompletableFuture<Void> initializeMenus() {
        if (menuRepository.count() == 0) {
            menuRepository.saveAll(menus);
        }
        return CompletableFuture.completedFuture(null);
    }
    @Async
    public CompletableFuture<Void> initializeGroups() {
        if (groupRepository.count() == 0) {
            groupRepository.saveAll(groups);
        }
        return CompletableFuture.completedFuture(null);
    }
    @Async
    public CompletableFuture<Void> initializePermissions() {
        if (permissionRepository.count() == 0) {
            permissionRepository.saveAll(permissions);
        }
        return CompletableFuture.completedFuture(null);
    }
    @Async
    public CompletableFuture<Void> initializeRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(roles);
        }
        return CompletableFuture.completedFuture(null);
    }
    @Async
    public CompletableFuture<Void> initializeUsers() {
        if (userRepository.count() == 0) {
            userRepository.saveAll(users);
        }
        return CompletableFuture.completedFuture(null);
    }
    @Async
    public CompletableFuture<Void> initializeApplications() {
        if (applicationRepository.count() == 0) {
            applicationRepository.saveAll(applications);
        }
        return CompletableFuture.completedFuture(null);
    }
    @Async
    public CompletableFuture<Void> initializeRolePermissions() {
        if (rolePermissionRepository.count() == 0) {
            rolePermissionRepository.saveAll(rolePermissions);
        }
        return CompletableFuture.completedFuture(null);
    }
    @Async
    public CompletableFuture<Void> initializePageUrls() {
        if (pageUrlRepository.count() == 0) {
            pageUrlRepository.saveAll(pageUrls);
        }
        return CompletableFuture.completedFuture(null);
    }
    @Async
    public CompletableFuture<Void> initializeUserGroups() {
        if (userGroupRepository.count() == 0) {
            userGroupRepository.saveAll(userGroups);
        }
        return CompletableFuture.completedFuture(null);
    }
    @Async
    public CompletableFuture<Void> initializeGroupRoles() {
        if (groupRoleRepository.count() == 0) {
            groupRoleRepository.saveAll(groupRoles);
        }
        return CompletableFuture.completedFuture(null);
    }
    /*
    Veritabanına ilk verileri girmesini sağlar
     */
    public void intialize(){
            /*
           Roller ve Yetkileri Tanımlanıyor.
           aşağıdaki sıraya göre sırasıyla işlem bittiğinde diğer işlem başlıyor.
         */

        //Veritabanına ilk grup bilgisini ekler
        initializeGroups().join();

        //Veritabanına ilk izin çeşitleri ekler
        initializePermissions().join();

        //Veritabanına ilk roller ekleniyor
        initializeRoles().join();

        //Veritabanına ilk kullanıcılar ekleniyor
        initializeUsers().join();

        //Kullanıcı Diğer Grupları
        initializeUserGroups().join();

        // veritabanına ilk uygulamalar ekler
        initializeApplications().join();

        //Veritabanına ilk grup bilgisini ekler
        initializeMenus().join();
        //Veritabanına uygulamalara ait ilk sayfalar yükleniyor
        initializePageUrls().join();

        //Role ait izinleri veritabanına kaydediyor
        initializeRolePermissions().join();

        initializeGroupRoles().join();
        /*

         */
    }
    @Override
    public void run(String... args) throws Exception {
        intialize();


    }
}
