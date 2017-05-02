package aid;

import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDSplashAd;
import com.mingli.toms.MenuActivity;

public class AdListener implements BDBannerAd.BannerAdListener,
		BDInterstitialAd.InterstitialAdListener, BDSplashAd.SplashAdListener {
	// private TextView tv;

	private MenuActivity acti;

	public AdListener(MenuActivity acti) {
		this.acti = acti;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAdvertisementDataDidLoadFailure() {
		this.println("    ad did load failure");
	}

	@Override
	public void onAdvertisementDataDidLoadSuccess() {
		this.println("    ad did load success");
	}

	@Override
	public void onAdvertisementViewDidClick() {
		this.println("    ad view did click");
		acti.adClicked();
	}

	@Override
	public void onAdvertisementViewDidShow() {
		this.println("    ad view did show");
		acti.adShow();
	}

	@Override
	public void onAdvertisementViewWillStartNewIntent() {
		this.println("    ad view will new intent");
	}

	@Override
	public void onAdvertisementViewDidHide() {
		this.println("    ad view did hide");
	}

	void println(String string) {
		// Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
		// tv.append(string+"\n");
		Log.i(string);
	}
}