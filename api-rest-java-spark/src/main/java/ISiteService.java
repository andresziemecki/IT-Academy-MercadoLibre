import com.sun.jdi.request.ExceptionRequest;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.util.Collection;

public interface ISiteService {
    String getSites() throws IOException, BadLocationException;

    public String getCategories(String id) throws IOException, BadLocationException;
}
