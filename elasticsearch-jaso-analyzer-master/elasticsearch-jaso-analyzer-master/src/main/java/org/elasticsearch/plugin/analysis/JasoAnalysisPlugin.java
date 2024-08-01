package org.elasticsearch.plugin.analysis;

import org.elasticsearch.index.analysis.JasoAnalyzerProvider;
import org.elasticsearch.index.analysis.JasoTokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.Map;

import org.elasticsearch.indices.analysis.AnalysisModule;

import static java.util.Collections.singletonMap;

import org.apache.lucene.analysis.Analyzer;

/**
 * JasoAnalysisPlugin
 *
 * @author 최일규
 * @since 2018-03-21
 */
public class JasoAnalysisPlugin extends Plugin implements AnalysisPlugin {
    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        return singletonMap("jaso_analyzer", JasoAnalyzerProvider::new);
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
        return singletonMap("jaso_tokenizer", JasoTokenizerFactory::new);
    }
}