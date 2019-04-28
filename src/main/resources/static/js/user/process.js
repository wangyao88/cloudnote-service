function getProcess(password){
    if(password.length < 4){
        return 0;
    }
    var ls = 0;
    if (password.match(/[a-z]/ig)){
        ls++;
    }
    if (password.match(/[0-9]/ig)){
        ls++;
    }
    if (password.match(/(.[^a-z0-9])/ig)){
        ls++;
    }
    if (password.length < 6 && ls > 0){
        ls--;
    }
    if (password.length > 10){
        ls++;
    }
    var val = ls*25;
    var process = $('#process');
    process.css('width', val+'%');
    process.attr('aria-valuenow', val);
    process.html(val+'%');
    return val;
}