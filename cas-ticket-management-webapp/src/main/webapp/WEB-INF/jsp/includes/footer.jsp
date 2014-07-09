	</section>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		window.onload=function(e) {
			parent.postMessage(getDocumentHeight(), "*"); 

			function getDocumentHeight() {
				var body = document.body;
				var html = document.documentElement;
				return Math.max(body.scrollHeight, body.offsetHeight,
								html.clientHeight, html.scrollHeight, html.offsetHeight);
			}
		};
	</script>
</body>
</html>