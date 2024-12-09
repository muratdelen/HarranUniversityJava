package com.harran.oturum.init;

import com.harran.oturum.dao.*;
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
    private final PageUrlRepo pageUrlRepository;
    private final GroupRepo groupRepository;
    private final PermissionRepo permissionRepository;
    private final RoleRepo roleRepository;
    private final RolePermissionRepo rolePermissionRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final List<User> users = Arrays.asList(
            new User("muratdelen",encoder.encode("1234"),"Murat","DELEN","muratdelen@harran.edu.tr")
    );
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
    private final List<Application> applications = Arrays.asList(
            new Application("Kalite Yönetim Sistemi","açıklama", true, "193.168.1.1","//oturum.harran.edu.tr", users.get(0)),
            new Application("Geribildirim Sistemi", "Teşekkür, Takdir, Eleştiri, Talep, Dilek, İstek, Öneri, Şikayet", true, "193.168.1.1", "//geribildirim.harran.edu.tr", users.get(0)),
            new Application("Seçim Sistemi", "Tüm seçimler buradan yapılmaktadır.", true, "193.168.1.1", "//secim.harran.edu.tr", users.get(0)),
            new Application("Yönetime Katılım Sistemi", "Yönetim kararlarına katılabilirsiniz.", true, "193.168.1.1", "//katilim.harran.edu.tr", users.get(0)),
            new Application("Yönetime İş Takip Sistemi", "Yönetim yapmayı planladığı işlerin takibini sağlar.", true, "193.168.1.1", "//katilim.harran.edu.tr", users.get(0)),
            new Application("Veri Sistemi", "Tüm veri api bilgilerine buradan erişebilirsiniz.", true, "193.168.1.1", "//bigdata.harran.edu.tr", users.get(0)),
            new Application("Veri Takip Sistemi", "Tüm verileri belli peryodlar ile takip edebilirsiniz.", true, "193.168.1.1", "//takip.harran.edu.tr", users.get(0)),
            new Application("Veri Toplama Sistemi", "Tüm verileri buradan toplayabilirsiniz.", true, "193.168.1.1", "//verial.harran.edu.tr", users.get(0)),
            new Application("Veri Gösterme Sistemi", "Tüm verileri buradan yetkiniz dahilinde görebilirsiniz.", true, "193.168.1.1", "//verigoster.harran.edu.tr", users.get(0))
    );

    private final List<PageUrl> pageUrls = Arrays.asList(
            new PageUrl("Kalite Anasayfası", "HU Kalite uygulaması anasayfası", applications.get(0),"/","GET"),
            new PageUrl("Geri Bildirim Anasayfası", "HU Geri Bildirim uygulaması anasayfası", applications.get(1),"/","GET")
    );
    private final List<RolePermission> rolePermissions = List.of(
            new RolePermission("Role yetkisi","açıklama",roles.get(3), pageUrls.get(0), permissions.get(1))
    );

    public DataInitializer(UserRepo userRepository, ApplicationRepo applicationRepository, PageUrlRepo pageUrlRepository, GroupRepo groupRepository, PermissionRepo permissionRepository, RoleRepo roleRepository, RolePermissionRepo rolePermissionRepository) {
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.pageUrlRepository = pageUrlRepository;
        this.groupRepository = groupRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
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
    /*
    Veritabanına ilk verileri girmesini sağlar
     */
    @Override
    public void run(String... args) throws Exception {

        //Veritabanına ilk kullanıcılar ekleniyor
        initializeUsers().join();

        //Veritabanına ilk grup bilgisini ekler
        initializeGroups().join();

        //Veritabanına ilk izin çeşitleri ekler
        initializePermissions().join();

        //Veritabanına ilk roller ekleniyor
        initializeRoles().join();

        // veritabanına ilk uygulamalar ekler
        initializeApplications().join();

        //Veritabanına uygulamalara ait ilk sayfalar yükleniyor
        initializePageUrls().join();

        //Role ait izinleri veritabanına kaydediyor
        initializeRolePermissions().join();

    }
}
