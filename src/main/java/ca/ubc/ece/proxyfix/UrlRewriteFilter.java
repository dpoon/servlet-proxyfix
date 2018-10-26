//
// Copyright 2016 D. W. Poon and the University of British Columbia
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package ca.ubc.ece.proxyfix;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class UrlRewriteFilter implements Filter {

    private static class ProxyHttpRequest extends HttpServletRequestWrapper {
        ProxyHttpRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public StringBuffer getRequestURL() {
            StringBuffer url = super.getRequestURL();
            String host = this.getHeader("X-Forwarded-Host");
            if (host != null) {
                int hostStart = url.indexOf("://") + 3;
                if (hostStart < 3) return url;

                int pathStart = url.indexOf("/", hostStart);
                if (hostStart < 0) return url;

                url.replace(hostStart, pathStart, host);
            }
            return url;
        }
    }

    //////////////////////////////////////////////////////////////////////

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws ServletException, IOException {
        if (req instanceof HttpServletRequest) {
            req = new ProxyHttpRequest((HttpServletRequest)req);
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}
