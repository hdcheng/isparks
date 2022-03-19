package app.addons;

import app.addons.breeze.BreezeApp;
import app.addons.meet.MeetApp;
import app.isparks.core.framework.IBoot;

public class AddonBoot implements IBoot {


    @Override
    public void boot(Object... args) {
        new MeetApp().run();
        new BreezeApp().run();
    }

}
