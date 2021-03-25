com_company_clinic_screens_VisitEdit = function () {
    var connector = this;
    var element = connector.getElement();
    element.innerHTML = "<div id=\"editor\">" +
        "</div>";

    connector.onStateChange = function () {
        var state = connector.getState();
        var data = state.data;

        var quill = new Quill('#editor', data.options);

        // Subscribe on textChange event
        quill.on('text-change', function (delta, oldDelta, source) {
            if (source === 'user') {
                console.log(source)
                connector.valueChanged(quill.getText(), quill.getContents());
            }
        });
        connector.setText = function (text) {
            console.log(text);
            quill.setText(text, 'api');
        }
    }
};