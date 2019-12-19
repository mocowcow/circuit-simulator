package event;

import java.util.LinkedList;

public abstract class InputEventEmitter {

    protected LinkedList<InputListener> listenerList = new LinkedList<>();

    final protected void notifyAllListener() {
        // System.out.println("called " + this.getClass() + " notifyAllListener");
        listenerList.forEach(x -> {
            x.onInputChanged();
        });
    }

    final protected void addInputListener(InputListener il) {
        listenerList.add(il);
    }
;

}
