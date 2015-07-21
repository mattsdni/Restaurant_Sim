package States;

import controlP5.ControlEvent;

/**
 * Created by Matt on 5/11/2015.
 */
public interface IState
{
    public void Update(double elapsedTime);

    public void Render();

    public void OnEnter();

    public void OnExit();

    public void receiveEvents(ControlEvent event);
}
