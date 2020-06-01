package cc.liqingsong.api.admin.realm;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * Admin JwtSubject
 * @author liqingsong
 */
public class JwtDefaultSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        // 不创建 session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
