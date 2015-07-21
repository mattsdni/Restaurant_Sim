package Main;

import States.IState;

import java.util.Stack;

/**
 * Created by Matt on 5/11/2015.
 */
public class StateMachine
{
    public Stack<IState> mStack = new Stack<IState>();

    private double timeElapsed = 0;
    private long startTime = System.currentTimeMillis();
    private long thisFrame;
    private long lastFrame = 0;

    public boolean minimized = false;

    public void Update()
    {
        if(mStack.isEmpty())
        {
            KeyHook.unblockWindowsKey();
            System.exit(0);
        }
        thisFrame = System.currentTimeMillis()-startTime;
        timeElapsed = (thisFrame-lastFrame)/1000.0;

        mStack.peek().Update(timeElapsed);
    }

    public void Render()
    {
        if (!minimized)
            mStack.peek().Render();
        lastFrame = thisFrame;
    }

    public void Push(IState state)
    {
        mStack.push(state);
        mStack.peek().OnEnter();
    }

    public void Pop()
    {
        mStack.peek().OnExit();
        mStack.pop();
    }
}
