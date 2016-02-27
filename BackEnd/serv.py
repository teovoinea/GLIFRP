import cherrypy
class RequestProcessingWebService(object):
	exposed = True

	@cherrypy.tools.accept(media="text/javascript")
	def GET(self):
		return "Should be polling POST"

	def POST(self, latitude, longitude, zip_code):
		r = "You posted lat: {0}, long: {1}, zip: {2}".format(latitude, longitude, zip_code)
		return r

if __name__ == '__main__':
	conf = {
		'/': {
			'request.dispatch': cherrypy.dispatch.MethodDispatcher(),
			'tools.response_headers.on': True,
			'tools.response_headers.headers': [('Content-Type', 'text/javascript')],
		}
	}
	cherrypy.quickstart(RequestProcessingWebService(), '/search', conf)