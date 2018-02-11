wrk.method = "GET"
counter = 0
repeatNum = 749

request = function()
	path = "/v0/entity?id=" .. counter
	counter = (counter + 1) % repeatNum
	return wrk.format(nil, path)
end
	