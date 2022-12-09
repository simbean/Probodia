import logging, logstash

log_format = logging.Formatter('\n[%(levelname)s|%(name)s|%(filename)s:%(lineno)s] %(asctime)s > %(message)s')
def create_logger(logger_name, url):
    logger = logging.getLogger(logger_name)
    logger.setLevel(logging.INFO)
    logger.addHandler(logstash.TCPLogstashHandler(url, 5000, version=1))
    return logger