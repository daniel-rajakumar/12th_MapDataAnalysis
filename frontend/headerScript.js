let btns = document.querySelectorAll('.header-right a');

btns.forEach( button => {
// set click event
button.addEventListener('click', function() {
    //remove all the active class from buttons
    btns.forEach( oldButton => {
        oldButton.classList.remove('active');
    });

    //append the active class to the new clicked button
    this.classList.add('active');
})
})
