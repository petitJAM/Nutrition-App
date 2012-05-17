package app.nutrition.test;

import java.util.Locale;

import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;
import app.nutrition.NutritionAppActivity;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author Alex Petitjean.
 *         Created May 11, 2012.
 */
public class L10NTest extends ActivityInstrumentationTestCase2<NutritionAppActivity> {

	private NutritionAppActivity naa;
	private Resources res;
	private DisplayMetrics dm;
	private String en = "en";
	private String fr = "fr";
	private String de = "de";
	private String es = "es";

	private String app_name, logo_content_description, camera_button_text,
			search_button_text, settings_button_text, info_button_text, exit_button_text,
			wait_dialog, no_response_server, OK;

	/**
	 * Test Localization of the app.
	 * 
	 * @param activityClass
	 */
	public L10NTest() {
		super(NutritionAppActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		naa = getActivity();
		res = naa.getResources();
		dm = res.getDisplayMetrics();
	}

	/**
	 * Test English settings
	 */
	public void testEnglishStrings() {
		android.content.res.Configuration conf = res.getConfiguration();
		conf.locale = new Locale(en.toLowerCase());
		res.updateConfiguration(conf, dm);
		
		getStrings();
		assertEquals("Nutrition App", app_name);
		assertEquals("Logo", logo_content_description);
		assertEquals("Capture Image", camera_button_text);
		assertEquals("Search", search_button_text);
		assertEquals("Settings", settings_button_text);
		assertEquals("About Us", info_button_text);
		assertEquals("Exit", exit_button_text);
		assertEquals("Waiting for server…", wait_dialog);
		assertEquals("Failed to receive information from server!", no_response_server);
		assertEquals("OK", OK);
//		assertEquals("Welcome to Nutrition App!\nThis application is your very smart, very adorable companion.\nIf you\'re ever curious about what is hiding in your food, just take a quick snapshot using the camera button, and watch as computer magic sends its nutrition info to your phone!\nThis application was designed by three poor, starving college students, so give us money!    ", about_us);
	}

	/**
	 * Test French settings
	 */
	public void testFrenchStrings() {
		android.content.res.Configuration conf = res.getConfiguration();
		conf.locale = new Locale(fr.toLowerCase());
		res.updateConfiguration(conf, dm);

		getStrings();
		assertEquals("Nutrition App", app_name);
		assertEquals("Logo", logo_content_description);
		assertEquals("Capturer une image", camera_button_text);
		assertEquals("Rechercher", search_button_text);
		assertEquals("Paramètres", settings_button_text);
		assertEquals("A propos de nous", info_button_text);
		assertEquals("Sortie", exit_button_text);
		assertEquals("Attente pour le serveur…", wait_dialog);
		assertEquals("Impossible de recevoir des informations depuis le serveur!", no_response_server);
		assertEquals("OK", OK);
//		assertEquals("Bienvenue sur App nutrition!\nCette application est votre très intelligent, très adorable compagnon.\nSi jamais vous êtes curieux de savoir ce qui se cache dans votre alimentation, il suffit de prendre un instantané rapide en utilisant le bouton de la caméra, et regardez la magie ordinateur envoie son info nutrition à votre téléphone!\nCette application a été conçu par trois étudiants de niveau collégial, les pauvres meurent de faim, afin de nous donner de l\'argent!", about_us);
	}

	/**
	 * Test German settings
	 */
	public void testGermanStrings() {
		android.content.res.Configuration conf = res.getConfiguration();
		conf.locale = new Locale(de.toLowerCase());
		res.updateConfiguration(conf, dm);
		
		getStrings();
		assertEquals("Ernährung App", app_name);
		assertEquals("Logo", logo_content_description);
		assertEquals("Bild aufnehmen", camera_button_text);
		assertEquals("Suche", search_button_text);
		assertEquals("Einstellungen", settings_button_text);
		assertEquals("Wir über uns", info_button_text);
		assertEquals("verlassen", exit_button_text);
		assertEquals("Warten auf den Server…", wait_dialog);
		assertEquals("Konnte Informationen vom Server zu empfangen!", no_response_server);
		assertEquals("OK", OK);
//		assertEquals("Willkommen bei Nutrition App!\nDiese Anwendung ist Ihr sehr klug, sehr liebenswert Begleiter.\nWenn Sie schon einmal neugierig, was in Ihrem Essen versteckt sind, Werfen Sie einen schnellen Überblick über die Kamera-Taste, und zusehen, wie Computer Magie sendet seine Ernährung Info auf Ihr Handy!\nDiese Anwendung wurde von drei armen, hungernden Studenten konzipiert, so geben uns Geld!", about_us);
	}

	/**
	 * Test Spanish settings
	 */
	public void testSpanishStrings() {
		android.content.res.Configuration conf = res.getConfiguration();
		conf.locale = new Locale(es.toLowerCase());
		res.updateConfiguration(conf, dm);
		
		getStrings();
		assertEquals("Nutrición App", app_name);
		assertEquals("Logotipo", logo_content_description);
		assertEquals("Captura de Imagen", camera_button_text);
		assertEquals("Búsqueda", search_button_text);
		assertEquals("Ajustes", settings_button_text);
		assertEquals("Sobre Nosotros", info_button_text);
		assertEquals("Salida", exit_button_text);
		assertEquals("Esperando que el servidor…", wait_dialog);
		assertEquals("¡No se pudo recibir la información desde el servidor!", no_response_server);
		assertEquals("OK", OK);
//		assertEquals("Bienvenido a la aplicación de Nutrición!\nEsta aplicación es su muy inteligente, muy adorable compañera.\nSi alguna vez curiosidad por saber qué se esconde en los alimentos, acaba de tomar una instantánea rápida mediante el botón de la cámara, y ver como la magia computadora envía la información de nutrición para tu teléfono!\nEsta aplicación fue diseñada por tres estudiantes universitarios pobres, hambrientos, así que nos dan dinero!", about_us);
	}

	private void getStrings() {
		app_name = naa.getString(app.nutrition.R.string.app_name);
		logo_content_description = naa
				.getString(app.nutrition.R.string.logo_content_description);
		camera_button_text = naa.getString(app.nutrition.R.string.camera_button_text);
		search_button_text = naa.getString(app.nutrition.R.string.search_button_text);
		settings_button_text = naa.getString(app.nutrition.R.string.settings_button_text);
		info_button_text = naa.getString(app.nutrition.R.string.info_button_text);
		exit_button_text = naa.getString(app.nutrition.R.string.exit_button_text);
		wait_dialog = naa.getString(app.nutrition.R.string.wait_dialog);
		no_response_server = naa.getString(app.nutrition.R.string.no_response_server);
		OK = naa.getString(app.nutrition.R.string.ok);
		naa.getString(app.nutrition.R.string.about_us);
	}
}
