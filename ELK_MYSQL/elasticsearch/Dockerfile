ARG ELASTIC_VERSION

# https://www.docker.elastic.co/
FROM docker.elastic.co/elasticsearch/elasticsearch:${ELASTIC_VERSION}

# Add your elasticsearch plugins setup here
# Example: RUN elasticsearch-plugin install analysis-icu

COPY plugin/jaso-analyzer-plugin-8.11.4-plugin.zip /usr/share/elasticsearch/plugin/jaso-analyzer-plugin-8.11.4-plugin.zip

RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install file:///usr/share/elasticsearch/plugin/jaso-analyzer-plugin-8.11.4-plugin.zip
