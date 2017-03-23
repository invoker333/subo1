package aid;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.appx.BDAppWallAd;
import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDSplashAd;
import com.mingli.toms.MenuActivity;

public class Ad {
	private BDBannerAd bannerview;
	private BDInterstitialAd interstitialAd;
	private BDAppWallAd appWallAd;
	private String SDK_APP_KEY = "Q0bAC8QCCUVAjVp6mqZUOdvvc3RSDKvE";
	private String SDK_BANNER_AD_ID = "dGmciPVYfqrDmbORIuANRwDp";
	private String SDK_SPLASH_AD_ID = "BkHxDG2WkDxW6175vbTAbIiK";
	private String SDK_INTERSTITIAL_AD_ID = "aSh0xW4nZw6SKd6oGfGC0llM";
	private String SDK_APPWALL_AD_ID = "4MXqTpMlS8hd9ya9indMWpdT";
	private MenuActivity acti;
	private AdListener adl;

	public Ad(MenuActivity acti) {
		this.acti = acti;
		this.adl = new AdListener(acti);
	}

	public void removeView(View view) {
		// TODO Auto-generated method stub
		if (view != null && view.getParent() != null) {
			((ViewGroup) view.getParent()).removeView(view);
			// ((ViewGroup) view.getParent()).removeViewAt(index);
			// ((ViewGroup) view.getParent()).removeAllViews();
		}
		// addView(view);
	}

	public void showBanner(ViewGroup container) {
		try {
			if (null == getBannerview()) {
				// println("---- bannerAd start to show ----");
				setBannerview(new BDBannerAd(acti, SDK_APP_KEY,
						SDK_BANNER_AD_ID));
				getBannerview().setAdListener(adl);
				// ViewGroup container = (ViewGroup)
				// findViewById(R.id.container);
				getBannerview().setAdSize(BDBannerAd.SIZE_FLEXIBLE);
			} else {
				println("---- bannerAd is showing, should hide first");
			}

			if (container != null)
				container.addView(getBannerview());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hideBanner(ViewGroup container) {
		if (getBannerview() != null) {
			// = (ViewGroup) findViewById(R.id.container);
			container.removeAllViews();
			getBannerview().destroy();
			setBannerview(null);
			println("---- bannerAd is hidden ----");
		} else {
			println("---- bannerAd not found ----");
		}
	}

	public void loadInterstitial() {
		// println("---- interstitialAd is loading ----");
		if (null == interstitialAd) {
			interstitialAd = new BDInterstitialAd(acti, SDK_APP_KEY,
					SDK_INTERSTITIAL_AD_ID);
			interstitialAd.setAdListener(adl);
		}
		try {
			interstitialAd.loadAd();
		} catch (Exception e) {

		}
	}

	public void showInterstitial() {
		try {
			if (null == interstitialAd || !interstitialAd.isLoaded()) {
				println("---- interstitialAd is not ready ----");
			} else {
				println("---- interstitialAd start to show ----");
			}
			interstitialAd.showAd();
		} catch (Exception e) {
		}
	}

	void hideInterstitial() {
		try {

			if (interstitialAd != null) {
				interstitialAd.destroy();
				interstitialAd = null;
				println("---- interstitialAd hided ----");
			} else {
				println("---- interstitialAd not ready ----");
			}
		} catch (Exception e) {

		}

	}

	public void loadAppWallAd() {
		if (null == appWallAd) {
			appWallAd = new BDAppWallAd(acti, SDK_APP_KEY, SDK_APPWALL_AD_ID);
		}
		appWallAd.loadAd();
	}

	public void showAppWallAd() {
		if (appWallAd != null) {
			if (appWallAd.isLoaded()) {
				appWallAd.doShowAppWall();
				println("appWallShowed");
			} else {
				println("appWallNotReady");
				loadAppWallAd();
			}
		}

	}

	private BDSplashAd splashAd;

	public void loadSplashAd(View v) {
		if (null == splashAd) {
			splashAd = new BDSplashAd(acti, SDK_APP_KEY, SDK_SPLASH_AD_ID);
			splashAd.setAdListener(adl);
		}

		println("---- splash ad is loading");
	}

	public void showSplashAd(View v) {
		if (splashAd != null) {
			if (splashAd.isLoaded()) {
				splashAd.showAd();
			} else {
				splashAd.loadAd();
				println("---- splash ad is not ready");
			}
		}

	}

	void println(String string) {
		// Toast.makeText(acti, string, Toast.LENGTH_SHORT).show();
		adl.println(string);
	}

	public BDAppWallAd getAppWallAd() {
		return appWallAd;
	}

	public void setAppWallAd(BDAppWallAd appWallAd) {
		this.appWallAd = appWallAd;
	}

	public BDBannerAd getBannerview() {
		return bannerview;
	}

	public void setBannerview(BDBannerAd bannerview) {
		this.bannerview = bannerview;
	}
}
