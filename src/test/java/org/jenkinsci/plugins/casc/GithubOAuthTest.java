package org.jenkinsci.plugins.casc;

import hudson.security.SecurityRealm;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.GithubSecurityRealm;
import org.jenkinsci.plugins.casc.misc.ConfiguredWithCode;
import org.jenkinsci.plugins.casc.misc.EnvVarsRule;
import org.jenkinsci.plugins.casc.misc.JenkinsConfiguredWithCodeRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Purpose:
 *  Test that we can configure: <a href="https://plugins.jenkins.io/github-oauth"/>
 */
public class GithubOAuthTest {

    @Rule
    public RuleChain rc = RuleChain.outerRule(new EnvVarsRule().env("GITHUB_SECRET","j985j8fhfhh377"))
            .around(new JenkinsConfiguredWithCodeRule());
    @Test
    @ConfiguredWithCode("GithubOAuth.yml")
    public void testSampleVersionForOAuth() {
        SecurityRealm realm = Jenkins.getInstance().getSecurityRealm();
        assertThat(realm, instanceOf(GithubSecurityRealm.class));
        GithubSecurityRealm gsh = (GithubSecurityRealm)realm;
        assertEquals("someId", gsh.getClientID());
        assertEquals("https://api.github.com", gsh.getGithubApiUri());
        assertEquals("https://github.com", gsh.getGithubWebUri());
        assertEquals("j985j8fhfhh377", gsh.getClientSecret().getPlainText());
        assertEquals("read:org,user:email", gsh.getOauthScopes());
    }
}
