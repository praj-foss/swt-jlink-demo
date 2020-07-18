package in.praj.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class SwtApp {
    public static void main(String[] args) {
        var display = new Display();
        var shell = new Shell(display);
        shell.setLayout(new GridLayout(1, false));
        shell.setText("SWT Demo");
        shell.setSize(300, 200);

        var label = new Label(shell, SWT.NONE);
        label.setText("Hello World!");
        label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

        shell.open();
        while (! shell.isDisposed())
            if (! display.readAndDispatch())
                display.sleep();
        display.dispose();
    }
}
