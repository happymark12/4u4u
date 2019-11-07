package _4u4u_init.config;

import java.util.EnumSet;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionTrackingMode;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import _4u4u_init.filter.DisableCacheFilter;
import _4u4u_init.filter.FindUserPassword;
import _4u4u_init.filter.LoginCheckingFilter;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	// getRootConfigClasses()會傳回提供組態資訊(Java based
	// configuration)的Java類別，本例為RootAppConfig
	// 類別，它說明應用系統中，提供Service/Dao功能之Bean的組態資訊，例如提供DataSource類別、
	// 交易管理器(Transaction managers)、Hibernate的SessionFactory類別等。
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootAppConfig.class };
	}

	// WebAppConfig類別說明本應用系統的組態資訊，如通知分派器要到那些資料夾去找視圖檔、告訴Spring MVC
	// 那些套件下有控制器類別等受Spring控管的類別、檔案上傳時會用到的類別、哪些路徑下有靜態檔案，這些靜態
	// 檔案直接由容器傳回給客戶端而不要交給控制器去處理等等。
	@Override
	protected Class<?>[] getServletConfigClasses() { // 取代Spring的組態檔
		return new Class[] {WebSocketConfig.class, WebAppConfig.class };
	}

	@Override
	// 定義DispatcherServlet的ServletMapping，"/"表示分派器要處理所有請求。
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		LoginCheckingFilter loginCheckingFilter = new LoginCheckingFilter();
		FindUserPassword findUserPasswordFilter = new FindUserPassword();
		DisableCacheFilter disableCacheFilter = new DisableCacheFilter();
		return new Filter[] {characterEncodingFilter,findUserPasswordFilter,loginCheckingFilter,disableCacheFilter};
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig( new MultipartConfigElement(LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD));
	}


	private static final String LOCATION = ""; // Temporary location where files will be stored

	private static final long MAX_FILE_SIZE = 10971520; // 10MB : Max file size.
														// Beyond that size spring will throw exception.
	private static final long MAX_REQUEST_SIZE = 100971520; // 100MB : Total request size containing Multi part.
	
	private static final int FILE_SIZE_THRESHOLD = 5242880; // 5MB Size threshold after which files will be written to disk


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		
	}
	
	
}
