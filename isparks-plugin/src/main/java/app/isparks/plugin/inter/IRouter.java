package app.isparks.plugin.inter;

import app.isparks.plugin.vo.Button;
import app.isparks.plugin.vo.Menu;

/**
 * @author chenghd
 */
public interface IRouter {

    void addButton(Button button);

    void removeButton(Button button);

    void addMenu(Menu menu);

    void removeMenu(Menu menu);

}
