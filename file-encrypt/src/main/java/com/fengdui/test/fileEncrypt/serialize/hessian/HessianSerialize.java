
package com.fengdui.test.fileEncrypt.serialize.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.fengdui.test.fileEncrypt.serialize.Serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author FD
 * @date 2016/12/30
 * hessian序列化实现
 */
public class HessianSerialize implements Serialize {

    public void serialize(OutputStream output, Object object) {
        Hessian2Output ho = new Hessian2Output(output);
        try {
            ho.startMessage();
            ho.writeObject(object);
            ho.completeMessage();
            ho.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object deserialize(InputStream input) {
        Object result = null;
        try {
            Hessian2Input hi = new Hessian2Input(input);
            hi.startMessage();
            result = hi.readObject();
            hi.completeMessage();
            hi.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
