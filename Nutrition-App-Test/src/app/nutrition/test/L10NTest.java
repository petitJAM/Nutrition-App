package app.nutrition.test;

import java.util.Locale;

import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;
import android.util.Log;
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
			wait_dialog, no_response_server, OK, about_us;

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
		
		logAllStrings();

		getStrings();
		assertEquals("Nutrition App", app_name);
		assertEquals("Logo", logo_content_description);
		assertEquals("Capture Image", camera_button_text);
		assertEquals("Search", search_button_text);
		assertEquals("Settings", settings_button_text);
		assertEquals("About Us", info_button_text);
		assertEquals("Exit", exit_button_text);
		assertEquals("Waiting for server�", wait_dialog);
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
		
		logAllStrings();

		getStrings();
		assertEquals("Nutrition App", app_name);
		assertEquals("Logo", logo_content_description);
		assertEquals("Capturer une image", camera_button_text);
		assertEquals("Rechercher", search_button_text);
		assertEquals("Param�tres", settings_button_text);
		assertEquals("A propos de nous", info_button_text);
		assertEquals("Sortie", exit_button_text);
		assertEquals("Attente pour le serveur�", wait_dialog);
		assertEquals("Impossible de recevoir des informations depuis le serveur!", no_response_server);
		assertEquals("OK", OK);
//		assertEquals("Bienvenue sur App nutrition!\nCette application est votre tr�s intelligent, tr�s adorable compagnon.\nSi jamais vous �tes curieux de savoir ce qui se cache dans votre alimentation, il suffit de prendre un instantan� rapide en utilisant le bouton de la cam�ra, et regardez la magie ordinateur envoie son info nutrition � votre t�l�phone!\nCette application a �t� con�u par trois �tudiants de niveau coll�gial, les pauvres meurent de faim, afin de nous donner de l\'argent!", about_us);
	}

	/**
	 * Test German settings
	 */
	public void testGermanStrings() {
		android.content.res.Configuration conf = res.getConfiguration();
		conf.locale = new Locale(de.toLowerCase());
		res.updateConfiguration(conf, dm);
		
		logAllStrings();

		getStrings();
		assertEquals("Ern�hrung App", app_name);
		assertEquals("Logo", logo_content_description);
		assertEquals("Bild aufnehmen", camera_button_text);
		assertEquals("Suche", search_button_text);
		assertEquals("Einstellungen", settings_button_text);
		assertEquals("Wir �ber uns", info_button_text);
		assertEquals("verlassen", exit_button_text);
		assertEquals("Warten auf den Server�", wait_dialog);
		assertEquals("Konnte Informationen vom Server zu empfangen!", no_response_server);
		assertEquals("OK", OK);
//		assertEquals("Willkommen bei Nutrition App!\nDiese Anwendung ist Ihr sehr klug, sehr liebenswert Begleiter.\nWenn Sie schon einmal neugierig, was in Ihrem Essen versteckt sind, Werfen Sie einen schnellen �berblick �ber die Kamera-Taste, und zusehen, wie Computer Magie sendet seine Ern�hrung Info auf Ihr Handy!\nDiese Anwendung wurde von drei armen, hungernden Studenten konzipiert, so geben uns Geld!", about_us);
	}

	/**
	 * Test Spanish settings
	 */
	public void testSpanishStrings() {
		android.content.res.Configuration conf = res.getConfiguration();
		conf.locale = new Locale(es.toLowerCase());
		res.updateConfiguration(conf, dm);
		
		logAllStrings();

		getStrings();
		assertEquals("Nutrici�n App", app_name);
		assertEquals("Logotipo", logo_content_description);
		assertEquals("Captura de Imagen", camera_button_text);
		assertEquals("B�squeda", search_button_text);
		assertEquals("Ajustes", settings_button_text);
		assertEquals("Sobre Nosotros", info_button_text);
		assertEquals("Salida", exit_button_text);
		assertEquals("Esperando que el servidor�", wait_dialog);
		assertEquals("�No se pudo recibir la informaci�n desde el servidor!", no_response_server);
		assertEquals("OK", OK);
//		assertEquals("Bienvenido a la aplicaci�n de Nutrici�n!\nEsta aplicaci�n es su muy inteligente, muy adorable compa�era.\nSi alguna vez curiosidad por saber qu� se esconde en los alimentos, acaba de tomar una instant�nea r�pida mediante el bot�n de la c�mara, y ver como la magia computadora env�a la informaci�n de nutrici�n para tu tel�fono!\nEsta aplicaci�n fue dise�ada por tres estudiantes universitarios pobres, hambrientos, as� que nos dan dinero!", about_us);
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
		about_us = naa.getString(app.nutrition.R.string.about_us);
	}
	
	private void logAllStrings() {
		Log.d("Strings", "" + app_name);
		Log.d("Strings", "" + logo_content_description);
		Log.d("Strings", "" + camera_button_text);
		Log.d("Strings", "" + search_button_text);
		Log.d("Strings", "" + settings_button_text);
		Log.d("Strings", "" + info_button_text);
		Log.d("Strings", "" + exit_button_text);
		Log.d("Strings", "" + wait_dialog);
		Log.d("Strings", "" + no_response_server);
		Log.d("Strings", "" + OK);
		Log.d("Strings", "" + about_us);
	}
}