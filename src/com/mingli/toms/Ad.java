package com.mingli.toms;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.appx.BDAppWallAd;
import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDSplashAd;

public class Ad {
	private BDBannerAd bannerview;
	private BDInterstitialAd interstitialAd;
	private BDAppWallAd appWallAd;
	private String SDK_APP_KEY = "Q0bAC8QCCUVAjVp6mqZUOdvvc3RSDKvE";
	private String SDK_BANNER_AD_ID = "dGmciPVYfqrDmbORIuANRwDp";
	private String SDK_SPLASH_AD_ID = "BkHxDG2WkDxW6175vbTAbIiK";
	private String SDK_INTERSTITIAL_AD_ID = "aSh0xW4nZw6SKd6oGfGC0llM";
	private String SDK_APPWALL_AD_ID = "4MXqTpMlS8hd9ya9indMWpdT";
	private Activity acti;
	private AdListener adl;

	public Ad(Activity acti) {
		this.acti = acti;
		this.adl =new AdListener(null);
	}
	public void removeView(View view) {
		// TODO Auto-generated method stub
		if (view != null && view.getParent() != null) {
			((ViewGroup) view.getParent()).removeView(view);
//			((ViewGroup) view.getParent()).removeViewAt(index);
//			((ViewGroup) view.getParent()).removeAllViews();
		}
//		addView(view);
	}
	public void showBanner(ViewGroup container) {
		if (null == getBannerview()) {
//			println("---- bannerAd start to show ----");
			setBannerview(new BDBannerAd(acti, SDK_APP_KEY, SDK_BANNER_AD_ID));
			getBannerview().setAdListener(adl);
			// ViewGroup container = (ViewGroup) findViewById(R.id.container);
			getBannerview().setAdSize(BDBannerAd.SIZE_FLEXIBLE);
		} else {
//			println("---- bannerAd is showing, should hide first");
		}
		if(container!=null)container.addView(getBannerview());
	}

	void hideBanner(ViewGroup container) {
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
//		println("---- interstitialAd is loading ----");
		if (null == interstitialAd) {
			interstitialAd = new BDInterstitialAd(acti, SDK_APP_KEY,
					SDK_INTERSTITIAL_AD_ID);
			interstitialAd.setAdListener(adl);
		}
		interstitialAd.loadAd();
	}

	public void showInterstitial() {
		if (null == interstitialAd || !interstitialAd.isLoaded()) {
//			println("---- interstitialAd is not ready ----");
		} else {
//			println("---- interstitialAd start to show ----");
			interstitialAd.showAd();
		}
	}

	void hideInterstitial() {
		if (interstitialAd != null) {
			interstitialAd.destroy();
			interstitialAd = null;
//			println("---- interstitialAd hided ----");
		} else {
//			println("---- interstitialAd not ready ----");
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
				println("请选择感兴趣的内容");
			} else {
				println("应用墙没准备好");
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
		Toast.makeText(acti, string, Toast.LENGTH_SHORT).show();
//		adl.println(string);
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
	class AdListener implements  BDBannerAd.BannerAdListener, BDInterstitialAd.InterstitialAdListener, BDSplashAd.SplashAdListener {
		private TextView tv;

		public AdListener(TextView tv) {
			this.tv = tv;
		}

		@Override
		public void onAdvertisementDataDidLoadFailure() {
			this.println( "    ad did load failure");
		}

		@Override
		public void onAdvertisementDataDidLoadSuccess() {
			this.println( "    ad did load success");
		}

		@Override
		public void onAdvertisementViewDidClick() {
			this.println( "    ad view did click");
		}

		@Override
		public void onAdvertisementViewDidShow() {
			this.println( "    ad view did show");
		}

		@Override
		public void onAdvertisementViewWillStartNewIntent() {
			this.println( "    ad view will new intent");
		}

		@Override
		public void onAdvertisementViewDidHide() {
			this.println( "    ad view did hide");
		}

		void println( String string) {
//			Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
//			tv.append(string+"\n");
		}
	}
}
