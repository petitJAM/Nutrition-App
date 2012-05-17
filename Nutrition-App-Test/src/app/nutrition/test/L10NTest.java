package app.nutrition.test;

import java.util.Locale;

import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;
import app.nutrition.NutritionAppActivity;

/**
 * Test localization of strings.
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
	}
}
